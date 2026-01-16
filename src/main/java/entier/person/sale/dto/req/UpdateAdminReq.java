package entier.person.sale.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAdminReq {
    private String avatar;
    private String hoTen;
    private String email;
}
