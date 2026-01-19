package entier.person.sale.dto.res;

import lombok.Data;

@Data
public class LoaiSpRes {
    Long id;
    String ten;
    String hinhAnh;
    LoaiSpRes parent;
}
