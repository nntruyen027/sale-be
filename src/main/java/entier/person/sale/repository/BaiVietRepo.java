package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.BaiVietReq;
import entier.person.sale.dto.res.BaiVietRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class BaiVietRepo {

    private final DbFunctionExecutor dbFunctionExecutor;

    /* ================= DANH SÁCH ================= */

    public PageResponse<BaiVietRes> layDsBaiViet(
            Long chuyenMucId,
            String trangThai,
            String search,
            int page,
            int size
    ) {
        List<Object> params = new ArrayList<>();
        params.add(chuyenMucId);
        params.add(trangThai);
        params.add(search);
        params.add(page);
        params.add(size);

        return dbFunctionExecutor.execute(
                "blog.fn_lay_ds_bai_viet",
                params,
                new TypeReference<PageResponse<BaiVietRes>>() {
                }
        );
    }

    public PageResponse<BaiVietRes> layDsBaiVietTheoHashtag(
            String slug,
            int page,
            int size
    ) {
        List<Object> params = new ArrayList<>();
        params.add(slug);
        params.add(page);
        params.add(size);

        return dbFunctionExecutor.execute(
                "blog.fn_lay_ds_bai_viet_theo_hashtag",
                params,
                new TypeReference<PageResponse<BaiVietRes>>() {
                }
        );
    }

    public Long tangLuotXem(Long id) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        return dbFunctionExecutor.execute(
                "blog.fn_tang_luot_xem",
                params,
                Long.class
        );
    }

    public List<BaiVietRes> layDsBaiVietLienQuan(
            Long id
    ) {
        List<Object> params = new ArrayList<>();
        params.add(id);

        return dbFunctionExecutor.execute(
                "blog.fn_lay_bai_viet_lien_quan",
                params,
                new TypeReference<List<BaiVietRes>>() {
                }
        );
    }

    /* ================= CHI TIẾT ================= */

    public BaiVietRes layBaiViet(Long id) {
        List<Object> params = List.of(id);

        return dbFunctionExecutor.execute(
                "blog.fn_lay_bai_viet",
                params,
                BaiVietRes.class
        );
    }

    public BaiVietRes layBaiVietTheoSlug(String slug) {
        List<Object> params = List.of(slug);

        return dbFunctionExecutor.execute(
                "blog.fn_lay_bai_viet_theo_slug",
                params,
                BaiVietRes.class
        );
    }

    /* ================= THÊM ================= */

    public BaiVietRes themBaiViet(BaiVietReq req) {
        List<Object> params = new ArrayList<>();
        params.add(req.getTieuDe());
        params.add(req.getSlug());
        params.add(req.getTomTat());
        params.add(req.getNoiDung());
        params.add(req.getChuyenMucId());
        params.add(req.getTacGia());
        params.add(req.getTrangThai());
        params.add(req.getNguoiDang());

        return dbFunctionExecutor.execute(
                "blog.fn_them_bai_viet",
                params,
                BaiVietRes.class
        );
    }

    public BaiVietRes suaBaiViet(Long id, BaiVietReq req) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(req.getTieuDe());
        params.add(req.getSlug());
        params.add(req.getTomTat());
        params.add(req.getNoiDung());
        params.add(req.getChuyenMucId());
        params.add(req.getTacGia());
        params.add(req.getTrangThai());
        params.add(req.getNguoiDang());

        return dbFunctionExecutor.execute(
                "blog.fn_sua_bai_viet",
                params,
                BaiVietRes.class
        );
    }

    public void xoaBaiViet(Long id) {
        List<Object> params = new ArrayList<>();
        params.add(id);

        dbFunctionExecutor.execute(
                "blog.fn_xoa_bai_viet",
                params,
                Boolean.class
        );
    }

    public Boolean ganHashtagChoBaiViet(Long baiVietId, List<String> hashtags) {

        List<Object> params = new ArrayList<>();
        params.add(baiVietId);

        // PostgreSQL TEXT[] → truyền List<String> hoặc String[]
        params.add(hashtags == null ? new String[]{} : hashtags.toArray(new String[0]));

        return dbFunctionExecutor.execute(
                "blog.fn_gan_hashtag_cho_bai_viet",
                params,
                Boolean.class
        );
    }

}
