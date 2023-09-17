package open.filelink.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.filelink.config.AwsS3Client;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AWSFileUploadService implements FileUploadService {

    private static final String EMPTY_IMAGE_PATH = "classpath:static/empty_file.png";
    private final static int CONFLICT_MAX_RETRIES = 3;
    private final AwsS3Client client;
    private final ResourceLoader resourceLoader;


    @Override
    public String upload(MultipartFile file) {
        int retries = 0;
        String uuid;

        while (retries < CONFLICT_MAX_RETRIES) {
            uuid = UUID.randomUUID().toString();
            if (!client.isObjectExist(uuid)) {
                try {
                    client.uploadObject(uuid, file);
                    return uuid;
                } catch (Exception e) {
                    log.error("fail to upload file to s3. retry [{}/{}] ", retries + 1, CONFLICT_MAX_RETRIES, e);
                }
            }
            retries++;
        }
        return null;
    }

    @Override
    public byte[] read(String fileId) {
        return Optional.ofNullable(client.findObject(fileId))
                .orElseGet(() -> {
                    try {
                        log.info("unable to find file : {}",fileId);
                        return resourceLoader.getResource(EMPTY_IMAGE_PATH)
                                .getContentAsByteArray();
                    } catch (IOException e) {
                        return new byte[0];
                    }
                });
    }
}
