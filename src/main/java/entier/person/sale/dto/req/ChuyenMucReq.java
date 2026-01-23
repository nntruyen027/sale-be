package entier.person.sale.dto.req;

import lombok.Data;

@Data
public class ChuyenMucReq {
    private String ten;
    private String slug;
    private Long parentId;
}
