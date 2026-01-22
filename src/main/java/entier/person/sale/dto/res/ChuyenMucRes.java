package entier.person.sale.dto.res;

import lombok.Data;

@Data
public class ChuyenMucRes {
    private Long id;
    private String ten;
    private String slug;
    private ChuyenMucRes parent;
}
