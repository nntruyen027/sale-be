package entier.person.sale.dto.req;

import lombok.Data;

@Data
public class AdminRequest {
    String hoTen;
    String avatar;
    String username;
    String password;
    String repeatPassword;
}
