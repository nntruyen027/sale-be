package entier.person.sale.dto.res;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileResponse {
    private Long id;
    private String fileName;
    private String url;
    private String contentType;
    private Long size;
    private LocalDateTime uploadedAt;
}
