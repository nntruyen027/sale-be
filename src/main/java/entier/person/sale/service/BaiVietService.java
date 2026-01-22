package entier.person.sale.service;

import entier.person.sale.dto.req.BaiVietReq;
import entier.person.sale.dto.res.BaiVietRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.repository.BaiVietRepo;
import entier.person.sale.util.HashtagUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BaiVietService {
    private final BaiVietRepo baiVietRepo;

    public PageResponse<BaiVietRes> layDsBaiViet(
            Long chuyenMucId,
            String trangThai,
            String search,
            int page,
            int size
    ) {

        return baiVietRepo.layDsBaiViet(chuyenMucId, trangThai, search, page, size);
    }

    public PageResponse<BaiVietRes> layDsBaiVietTheoHashtag(
            String slug,
            int page,
            int size
    ) {
        return baiVietRepo.layDsBaiVietTheoHashtag(slug, page, size);
    }

    public Long tangLuotXem(Long id) {
        return baiVietRepo.tangLuotXem(id);
    }

    public List<BaiVietRes> layDsBaiVietLienQuan(
            Long id
    ) {
        return baiVietRepo.layDsBaiVietLienQuan(id);
    }

    /* ================= CHI TIẾT ================= */

    public BaiVietRes layBaiViet(Long id) {
        return baiVietRepo.layBaiViet(id);
    }

    public BaiVietRes layBaiVietTheoSlug(String slug) {
        return baiVietRepo.layBaiVietTheoSlug(slug);
    }

    /* ================= THÊM ================= */


    @Transactional
    public BaiVietRes themBaiViet(BaiVietReq req) {

        // 1️⃣ Thêm bài viết
        BaiVietRes baiViet = baiVietRepo.themBaiViet(req);

        // 2️⃣ Tách hashtag từ nội dung
        List<String> hashtags = HashtagUtil.extractFromContent(req.getNoiDung());

        // 3️⃣ Gán hashtag nếu có
        if (!hashtags.isEmpty()) {
            baiVietRepo.ganHashtagChoBaiViet(baiViet.getId(), hashtags);
        }

        return baiVietRepo.layBaiViet(baiViet.getId());
    }


    public BaiVietRes suaBaiViet(Long id, BaiVietReq req) {
        BaiVietRes baiViet = baiVietRepo.suaBaiViet(id, req);

        // 2️⃣ Tách lại hashtag từ nội dung mới
        List<String> hashtags = HashtagUtil.extractFromContent(req.getNoiDung());

        // 3️⃣ Gán lại hashtag (DB function đã xóa cũ)
        baiVietRepo.ganHashtagChoBaiViet(id, hashtags);

        return baiVietRepo.layBaiViet(baiViet.getId());
    }

    public void xoaBaiViet(Long id) {
        baiVietRepo.xoaBaiViet(id);
    }


}
