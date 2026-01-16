package entier.person.sale.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFullRes {
    private Long id;

    private String username;

    private String hoTen;

    private String email;
    
    private String avatar;

    private Timestamp createdAt;

    private List<String> roles;

    private List<String> permissions;

    private Boolean isActive;

}
