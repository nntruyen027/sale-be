package entier.person.sale.dto.res;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SanPhamRes {
    Long id;
    String ten;
    String hinhAnh;
    String moTa;
    Timestamp ngayTao;
    LoaiSpRes loai;
    List<BienTheRes> bienThe;
    Long gia;
    String slug;
}
