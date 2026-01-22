package entier.person.sale.dto.res;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BaiVietRes {
    private Long id;
    private String tieuDe;
    private String slug;
    private String tomTat;
    private String noiDung;

    private String tacGia;
    private String trangThai;
    private Long luotXem;

    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;

    private Long nguoiDang;
    private Long chuyenMucId;

    /**
     * JSONB từ view
     */
    private ChuyenMucRes chuyenMuc;

    /**
     * JSONB array
     */
    private List<HashtagRes> hashtags;
}
