package open.filelink.controller;

import org.springframework.web.bind.annotation.*;

@RestController("/api/file")
public class LinkController {

    @GetMapping
    public void getFileLink(){
    }

    @PostMapping
    public void uploadFile(){
    }

    @PatchMapping
    public void updateLink(){
    }

    @DeleteMapping
    public void deleteLink(){
    }
}
