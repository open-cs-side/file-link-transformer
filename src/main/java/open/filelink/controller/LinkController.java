package open.filelink.controller;

import lombok.RequiredArgsConstructor;
import open.filelink.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class LinkController {

    private final FileUploadService fileUploadService;

    @GetMapping
    public void getFileLink() {
    }

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        fileUploadService.upload(file);
    }

}
