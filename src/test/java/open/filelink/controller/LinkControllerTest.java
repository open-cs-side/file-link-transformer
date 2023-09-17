package open.filelink.controller;

import open.filelink.service.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LinkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileUploadService fileUploadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new LinkController(fileUploadService)).build();
    }

    @Test
    void getFileLink() throws Exception {
        String fileId = UUID.randomUUID().toString();
        byte[] fileData = new ClassPathResource("static/empty_file.png").getContentAsByteArray();

        when(fileUploadService.read(fileId)).thenReturn(fileData);

        mockMvc.perform(get("/api/file/{id}", fileId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(fileData));

        verify(fileUploadService, times(1)).read(fileId);
    }

    @Test
    void uploadFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "File content".getBytes());
        String fileId = UUID.randomUUID().toString();
        when(fileUploadService.upload(multipartFile)).thenReturn(fileId);

        mockMvc.perform(
                        multipart("/api/file")
                                .file(multipartFile)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(fileId));

        verify(fileUploadService, times(1)).upload(multipartFile);
    }
}