package open.filelink.service;

import open.filelink.config.AwsS3Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AWSFileUploadServiceTest {

    @Mock
    private AwsS3Client client;

    @Mock
    private ResourceLoader loader;

    @InjectMocks
    private AWSFileUploadService service;

    @Test
    @DisplayName("파일 업로드시 UUID가 반환됩니다")
    void shouldUploadFile() throws IOException {
        doNothing().when(client).uploadObject(anyString(), any());

        String fildId = service.upload(new MockMultipartFile("test.png", new byte[10]));

        assertDoesNotThrow(() -> UUID.fromString(fildId));
    }

    @Test
    @DisplayName("UUID 최대 3회 충돌시에는 null을 반환합니다")
    void shouldReturnNullWhenMaximumConflict() {
        doReturn(true).when(client).isObjectExist(anyString());

        String fildId = service.upload(new MockMultipartFile("test.png", new byte[10]));

        assertNull(fildId);
    }

    @Test
    void shouldReadFile() {
        byte[] file = new byte[10];
        doReturn(file).when(client).findObject(anyString());

        byte[] foundFile = service.read(UUID.randomUUID().toString());

        assertThat(file).isEqualTo(foundFile);
    }

    @Test
    void shouldReturnEmptyFile_whenFileDoesNotExist() throws IOException {
        ClassPathResource emptyFile = new ClassPathResource("static/empty_file.png");
        doReturn(emptyFile).when(loader).getResource("classpath:static/empty_file.png");
        doReturn(null).when(client).findObject(anyString());

        byte[] foundFile = service.read(UUID.randomUUID().toString());

        assertThat(foundFile).isEqualTo(emptyFile.getContentAsByteArray());
    }
}