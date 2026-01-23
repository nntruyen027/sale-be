package entier.person.sale.dto.req;

import lombok.Data;

@Data
public class BaiVietReq {
    private String tieuDe;
    private String slug;
    private String tomTat;
    private String noiDung;
    private String trangThai;
    private Long luotXem;
    private String tacGia;
    
    private Long chuyenMucId;
}
