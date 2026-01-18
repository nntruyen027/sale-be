package entier.person.sale.dto.req;

import lombok.Data;

@Data
public class DiaChiReq {
    private String chiTiet;
    private Long xaId;
    private String dinhVi;
    private Boolean isDefault;
}
