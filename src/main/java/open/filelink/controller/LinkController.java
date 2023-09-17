package open.filelink.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import open.filelink.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class LinkController {

    private final FileUploadService fileUploadService;

    @GetMapping("/{id}")
    public void getFileLink(HttpServletResponse response,
                            @PathVariable("id") String fileId) throws IOException {
        byte[] foundFile = fileUploadService.read(fileId);
        response.getOutputStream().write(foundFile);
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File does not Exist");
        }
        String fileId = fileUploadService.upload(file);
        return ResponseEntity.ok(fileId);
    }
}
