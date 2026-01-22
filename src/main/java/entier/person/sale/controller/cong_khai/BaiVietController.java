package entier.person.sale.controller.cong_khai;

import entier.person.sale.dto.res.BaiVietRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.service.BaiVietService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cong-khai/bai-viet")
@AllArgsConstructor
@Tag(name = "Bài viết công khai")
public class BaiVietController {

    private final BaiVietService baiVietService;

    // =========================
    // 1️⃣ DANH SÁCH
    // =========================
    @Operation(summary = "Danh sách bài viết công khai")
    @GetMapping
    public ResponseEntity<PageResponse<BaiVietRes>> layDsBaiViet(
            @RequestParam(required = false) Long chuyenMucId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                baiVietService.layDsBaiViet(chuyenMucId, "PUBLISHED", search, page, size)
        );
    }

    // =========================
    // 2️⃣ THEO SLUG
    // =========================
    @Operation(summary = "Xem chi tiết bài viết theo slug (tăng lượt xem)")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<BaiVietRes> layTheoSlug(@PathVariable String slug) {
        BaiVietRes res = baiVietService.layBaiVietTheoSlug(slug);
        baiVietService.tangLuotXem(res.getId());
        return ResponseEntity.ok(res);
    }

    // =========================
    // 3️⃣ THEO HASHTAG
    // =========================
    @Operation(summary = "Danh sách bài viết theo hashtag")
    @GetMapping("/hashtag/{slug}")
    public ResponseEntity<PageResponse<BaiVietRes>> layTheoHashtag(
            @PathVariable String slug,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                baiVietService.layDsBaiVietTheoHashtag(slug, page, size)
        );
    }

    // =========================
    // 4️⃣ LIÊN QUAN
    // =========================
    @Operation(summary = "Danh sách bài viết liên quan")
    @GetMapping("/{id}/lien-quan")
    public ResponseEntity<List<BaiVietRes>> layBaiVietLienQuan(@PathVariable Long id) {
        return ResponseEntity.ok(
                baiVietService.layDsBaiVietLienQuan(id)
        );
    }
}
