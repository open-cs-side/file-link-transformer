package open.filelink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FilelinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilelinkApplication.class, args);
    }

}
