package entier.person.sale.dto.res;

import lombok.Data;

@Data
public class BienTheRes {
    Long id;
    Long sanPhamId;
    String sku;
    String hinhAnh;
    String mauSac;
    String kichCo;
    String gia;
    Integer tonKho;
}
