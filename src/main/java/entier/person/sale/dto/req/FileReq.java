package entier.person.sale.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FileReq {
    private String fileName;
    private String storedName;
    private String url;
    private String contentType;
    private Long size;
    private Long userId;
    private LocalDateTime uploadedAt;
}
