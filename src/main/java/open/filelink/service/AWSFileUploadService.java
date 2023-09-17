package open.filelink.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.filelink.config.AwsS3Client;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AWSFileUploadService implements FileUploadService {

    private static final String EMPTY_IMAGE_PATH = "classpath:static/empty_file.png";
    private static final int CONFLICT_MAX_RETRIES = 3;

    private final AwsS3Client client;

    private final ResourceLoader resourceLoader;


    @Override
    public String upload(MultipartFile file) {
        return IntStream.range(0, CONFLICT_MAX_RETRIES)
                .mapToObj((retry) -> attemptUpload(retry, file))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }


    private String attemptUpload(int retry, MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        try {
            if (!client.isObjectExist(uuid)) {
                client.uploadObject(uuid, file);
                return uuid;
            }
        } catch (Exception e) {
            log.error("Failed to upload file to S3. Retry [{}]", retry, e);
        }
        return null;
    }

    @Override
    public byte[] read(String fileId) {
        return Optional.ofNullable(client.findObject(fileId))
                .orElseGet(() -> {
                    try {
                        log.info("unable to find file : {}", fileId);
                        return resourceLoader.getResource(EMPTY_IMAGE_PATH)
                                .getContentAsByteArray();
                    } catch (IOException e) {
                        return new byte[0];
                    }
                });
    }
}
