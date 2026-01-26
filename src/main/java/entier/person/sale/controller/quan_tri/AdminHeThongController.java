package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.BannerReq;
import entier.person.sale.dto.res.BannerRes;
import entier.person.sale.service.HeThongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quan-tri/he-thong")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý hệ thống dành cho Admin")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
public class AdminHeThongController {

    private final HeThongService heThongService;

    // =========================
    // 1️⃣ LẤY DANH SÁCH
    // =========================
    @Operation(summary = "Lấy danh sách banner")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/banner")
    public ResponseEntity<List<BannerRes>> layDsBanner(
    ) {
        return ResponseEntity.ok(heThongService.layDsBanner());
    }

    // =========================
    // 2️⃣ TẠO MỚI
    // =========================
    @Operation(summary = "Tạo mới bannerg")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping("/banner")
    public ResponseEntity<BannerRes> taoBanner(
            @RequestBody BannerReq bannerReq) {

        return ResponseEntity.ok(heThongService.taoBanner(bannerReq));
    }

    // =========================
    // 3️⃣ CẬP NHẬT
    // =========================
    @Operation(summary = "Cập nhật banner theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PutMapping("/banner/{id}")
    public ResponseEntity<BannerRes> suaBanner(
            @PathVariable Long id,
            @RequestBody BannerReq bannerReq) {

        return ResponseEntity.ok(heThongService.suaBanner(id, bannerReq));
    }

    // =========================
    // 4️⃣ XOÁ
    // =========================
    @Operation(summary = "Xoá banner theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @DeleteMapping("/banner/{id}")
    public ResponseEntity<?> xoaBanner(@PathVariable Long id) {

        heThongService.xoaBanner(id);
        return ResponseEntity.ok().build();
    }

    // =========================
    // 4️⃣ ài đặt phần giới thiệu trang chủ
    // =========================
    @Operation(summary = "Cài đặt phần giới thiệu trang chủ")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cài đặt thành công", content = @Content)
    @PostMapping("/home-gioithieu")
    public ResponseEntity<?> SetUpBannerGioiThieu(@RequestBody String object) {
        return ResponseEntity.ok(heThongService.SetUpBannerGioiThieu(object));
    }

    // =========================
    // 4️⃣ ài đặt phần giới thiệu trang chủ
    // =========================
    @Operation(summary = "Lấy phần giới thiệu trang chủ")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cài đặt thành công", content = @Content)
    @GetMapping("/home-gioithieu")
    public ResponseEntity<?> SetUpBannerGioiThieu() {
        return ResponseEntity.ok(heThongService.layHomeGioiThieu());
    }
}
