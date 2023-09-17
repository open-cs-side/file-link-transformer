package open.filelink.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class AwsS3Client {

    private final AmazonS3 amazonS3;

    private final String bucketName;

    public AwsS3Client(AwsCredential credential) {
        this.amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                credential.getAccessKey(),
                                credential.getSecretKey())
                ))
                .withRegion(Regions.fromName(credential.getRegion()))
                .build();
        this.bucketName = credential.getBucket();
    }

    public void uploadObject(String filePath, MultipartFile file) throws IOException {
        amazonS3.putObject(bucketName, filePath, file.getInputStream(), getMetaData(file));
    }

    private ObjectMetadata getMetaData(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        return metadata;
    }


    @SneakyThrows
    public byte[] findObject(String filePath) {
        if (isObjectExist(filePath)) {
            S3ObjectInputStream content = amazonS3.getObject(bucketName, filePath).getObjectContent();
            return IOUtils.toByteArray(content);
        }
        return null;
    }

    public boolean isObjectExist(String filePath) {
        return amazonS3.doesObjectExist(bucketName, filePath);
    }
}
