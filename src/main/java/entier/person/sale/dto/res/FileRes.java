package entier.person.sale.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FileRes {
    private Long id;
    private String fileName;
    private String storedName;
    private String url;
    private String contentType;
    private Long size;
    private Long userId;
    private LocalDateTime updatedAt;
}
