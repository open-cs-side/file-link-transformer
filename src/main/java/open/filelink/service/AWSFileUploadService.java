package open.filelink.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import open.filelink.config.AwsS3Client;
import open.filelink.entity.File;
import open.filelink.repository.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AWSFileUploadService implements FileUploadService {

    private static final String FILE_KEY_SEPARTOR = "_";

    private final AwsS3Client client;

    private final LinkRepository linkRepository;

    @SneakyThrows
    @Override
    public void upload(MultipartFile file) {
        String encodedFilename = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8.toString());
        Long fileKey = linkRepository.save(new File(encodedFilename)).getFileKey();

        String uniqueFilePath = encodedFilename + FILE_KEY_SEPARTOR + fileKey;

        client.uploadObject(uniqueFilePath, file);
    }

    @Override
    public void read() {

    }
}
