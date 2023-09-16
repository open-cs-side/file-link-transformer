package open.filelink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long fileKey;

    private String fileUrl;

    public File(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
