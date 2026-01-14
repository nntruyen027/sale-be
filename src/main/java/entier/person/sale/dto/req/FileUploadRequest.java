package entier.person.sale.dto.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadRequest {
    private MultipartFile file;
}
