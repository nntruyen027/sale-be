package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.ChuyenMucReq;
import entier.person.sale.dto.res.ChuyenMucRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.service.ChuyenMucService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/chuyen-muc")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý chuyên mục dành cho Admin")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BTYPE_READ.value())")
public class AdminChuyenMucController {

    private final ChuyenMucService chuyenMucService;

    // =========================
    // 1️⃣ LẤY DANH SÁCH
    // =========================
    @Operation(summary = "Lấy danh sách chuyên mục (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<ChuyenMucRes>> layDsChuyenMuc(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(chuyenMucService.layDsChuyenMuc(search, page, size));
    }

    // =========================
    // 2️⃣ TẠO MỚI
    // =========================
    @Operation(summary = "Tạo mới chuyên mục")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BTYPE_CREATE.value())")
    @PostMapping
    public ResponseEntity<ChuyenMucRes> taoChuyenMuc(
            @RequestBody ChuyenMucReq chuyenMucReq) {

        return ResponseEntity.ok(chuyenMucService.taoChuyenMuc(chuyenMucReq));
    }

    // =========================
    // 3️⃣ CẬP NHẬT
    // =========================
    @Operation(summary = "Cập nhật chuyên mục theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BTYPE_UPDATE.value())")
    @PutMapping("/{id}")
    public ResponseEntity<ChuyenMucRes> suaChuyenMuc(
            @PathVariable Long id,
            @RequestBody ChuyenMucReq chuyenMucReq) {

        return ResponseEntity.ok(chuyenMucService.suaChuyenMuc(id, chuyenMucReq));
    }

    // =========================
    // 4️⃣ XOÁ
    // =========================
    @Operation(summary = "Xoá chuyên mục theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).BTYPE_DELETE.value())")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaChuyenMuc(@PathVariable Long id) {

        chuyenMucService.xoaChuyenMuc(id);
        return ResponseEntity.ok().build();
    }
}
