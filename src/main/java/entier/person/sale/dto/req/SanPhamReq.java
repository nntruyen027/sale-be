package entier.person.sale.dto.req;

import lombok.Data;

@Data
public class SanPhamReq {
    Long loaiId;
    String ten;
    String hinhAnh;
    String moTa;
    String slug;
}