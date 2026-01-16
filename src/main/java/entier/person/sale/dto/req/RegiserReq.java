package entier.person.sale.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegiserReq {
    private String username;
    private String password;
    private String hoTen;
    private String email;
}
