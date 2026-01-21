package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.LoaiSpReq;
import entier.person.sale.dto.res.LoaiSpRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.service.LoaiSpService;
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
@RequestMapping("/quan-tri/loai-san-pham")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý loại sản phẩm dành cho Admin")
@PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PTYPE_READ)")
public class AdminLoaiSpController {

    private final LoaiSpService loaiSpService;

    // =========================
    // 1️⃣ LẤY DANH SÁCH
    // =========================
    @Operation(summary = "Lấy danh sách loại sản phẩm (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<LoaiSpRes>> layDsLoaiSp(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(loaiSpService.layDsLoaiSp(search, page, size));
    }

    // =========================
    // 2️⃣ TẠO MỚI
    // =========================
    @Operation(summary = "Tạo mới loại sản phẩm")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PTYPE_CREATE)")
    @PostMapping
    public ResponseEntity<LoaiSpRes> taoLoaiSp(
            @RequestBody LoaiSpReq loaiSpReq) {

        return ResponseEntity.ok(loaiSpService.taoLoaiSp(loaiSpReq));
    }

    // =========================
    // 3️⃣ CẬP NHẬT
    // =========================
    @Operation(summary = "Cập nhật loại sản phẩm theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PTYPE_UPDATE)")
    @PutMapping("/{id}")
    public ResponseEntity<LoaiSpRes> suaLoaiSp(
            @PathVariable Long id,
            @RequestBody LoaiSpReq loaiSpReq) {

        return ResponseEntity.ok(loaiSpService.suaLoaiSp(id, loaiSpReq));
    }

    // =========================
    // 4️⃣ XOÁ
    // =========================
    @Operation(summary = "Xoá loại sản phẩm theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PTYPE_DELETE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaLoaiSp(@PathVariable Long id) {

        loaiSpService.xoaLoaiSp(id);
        return ResponseEntity.ok().build();
    }
}
