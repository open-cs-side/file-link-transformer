package open.filelink.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "storage.s3")
@Getter
@Setter
public class AwsCredential {

    private String bucket;

    private String accessKey;

    private String secretKey;

    private String region;

}
