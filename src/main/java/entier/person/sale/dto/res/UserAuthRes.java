package entier.person.sale.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRes {
    private Long id;
    private String username;
    private String password;
    private String[] roles;
    private String[] permissions;
}
