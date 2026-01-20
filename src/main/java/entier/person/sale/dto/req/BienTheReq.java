package entier.person.sale.dto.req;

import lombok.Data;

@Data
public class BienTheReq {
    String sku;
    String hinhAnh;
    String mauSac;
    String kichCo;
    Long gia;
    Integer tonKho;
}
