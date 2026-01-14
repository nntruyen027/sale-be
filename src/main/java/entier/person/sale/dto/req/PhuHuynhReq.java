package entier.person.sale.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PhuHuynhReq {
    private String username;
    private String tenPhuHuynh;
    private String avatar;

    private Long hocSinhId;

    private String loaiPhuHuynh;
    private String soDienThoai;
    private String ngheNghiep;

    private LocalDate ngaySinhPhuHuynh;
    private Boolean laNam;

    private Long xaId;

    private Long tinhId;

    private String diaChiChiTiet;
}
