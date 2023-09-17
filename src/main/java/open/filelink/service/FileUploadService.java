package open.filelink.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String upload(MultipartFile file);

    byte[] read(String fileId);

}
