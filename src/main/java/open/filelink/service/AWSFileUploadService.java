package open.filelink.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import open.filelink.config.AwsS3Client;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AWSFileUploadService implements FileUploadService {

    private final static int CONFLICT_MAX_RETRIES = 3;
    private final AwsS3Client client;


    @SneakyThrows
    @Override
    public String upload(MultipartFile file) {
        int retries = 0;
        String uuid;

        while (retries < CONFLICT_MAX_RETRIES) {
            uuid = UUID.randomUUID().toString();
            if (!client.isObjectExist(uuid)) {
                client.uploadObject(uuid, file);
                return uuid;
            }
            retries++;
        }
        return null;
    }

    @Override
    public byte[] read(String fileId) {
        return client.findObject(fileId);
    }
}
