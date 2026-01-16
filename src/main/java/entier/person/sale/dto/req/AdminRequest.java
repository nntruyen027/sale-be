package entier.person.sale.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {
    String hoTen;
    String avatar;
    String username;
    String email;
    String password;
    String repeatPassword;
}
