package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.BaiVietReq;
import entier.person.sale.dto.res.BaiVietRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.service.BaiVietService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quan-tri/bai-viet")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý bài viết (Admin)")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BLOG_READ.value())")
public class AdminBaiVietController {

    private final BaiVietService baiVietService;

    /* =========================
     * 1️⃣ DANH SÁCH
     * ========================= */

    @Operation(summary = "Danh sách bài viết (lọc chuyên mục, trạng thái, tìm kiếm)")
    @SecurityApiResponses
    @GetMapping
    public ResponseEntity<PageResponse<BaiVietRes>> layDsBaiViet(
            @RequestParam(required = false) Long chuyenMucId,
            @RequestParam(required = false) String trangThai,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                baiVietService.layDsBaiViet(chuyenMucId, trangThai, search, page, size)
        );
    }

    @Operation(summary = "Danh sách bài viết theo hashtag (Admin)")
    @SecurityApiResponses
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

    /* =========================
     * 2️⃣ CHI TIẾT
     * ========================= */

    @Operation(summary = "Chi tiết bài viết theo ID")
    @SecurityApiResponses
    @GetMapping("/{id}")
    public ResponseEntity<BaiVietRes> layTheoId(@PathVariable Long id) {
        return ResponseEntity.ok(baiVietService.layBaiViet(id));
    }

    @Operation(summary = "Chi tiết bài viết theo slug (Admin)")
    @SecurityApiResponses
    @GetMapping("/slug/{slug}")
    public ResponseEntity<BaiVietRes> layTheoSlug(@PathVariable String slug) {
        return ResponseEntity.ok(baiVietService.layBaiVietTheoSlug(slug));
    }

    /* =========================
     * 3️⃣ BÀI VIẾT LIÊN QUAN
     * ========================= */

    @Operation(summary = "Lấy danh sách bài viết liên quan")
    @SecurityApiResponses
    @GetMapping("/{id}/lien-quan")
    public ResponseEntity<List<BaiVietRes>> layBaiVietLienQuan(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                baiVietService.layDsBaiVietLienQuan(id)
        );
    }



    /* =========================
     * 4️⃣ CRUD
     * ========================= */

    @Operation(summary = "Tạo mới bài viết")
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BLOG_CREATE.value())")
    @PostMapping
    public ResponseEntity<BaiVietRes> themBaiViet(
            @RequestBody BaiVietReq req
    ) {
        return ResponseEntity.ok(baiVietService.themBaiViet(req));
    }

    @Operation(summary = "Cập nhật bài viết")
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BLOG_UPDATE.value())")
    @PutMapping("/{id}")
    public ResponseEntity<BaiVietRes> suaBaiViet(
            @PathVariable Long id,
            @RequestBody BaiVietReq req
    ) {
        return ResponseEntity.ok(baiVietService.suaBaiViet(id, req));
    }

    @Operation(summary = "Xoá bài viết")
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BLOG_DELETE.value())")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaBaiViet(@PathVariable Long id) {
        baiVietService.xoaBaiViet(id);
        return ResponseEntity.ok().build();
    }
}
