package entier.person.sale.dto.res;

import lombok.Data;

@Data
public class DiaChiRes {
    private Long id;
    private String chiTiet;
    private XaRes xa;
    private String dinhVi;
    private Long userId;
    private Boolean isDefault;
}
