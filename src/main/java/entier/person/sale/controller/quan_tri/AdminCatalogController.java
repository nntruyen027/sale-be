package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.CatalogReq;
import entier.person.sale.dto.res.CatalogRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.service.CatalogService;
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
@RequestMapping("/quan-tri/catalog")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý catalog dành cho Admin")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).CATALOG_READ.value())")
public class AdminCatalogController {

    private final CatalogService catalogService;

    // =========================
    // 1️⃣ LẤY DANH SÁCH
    // =========================
    @Operation(summary = "Lấy danh sách catalog (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<CatalogRes>> layDsCatalog(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(catalogService.layDsCatalog(search, page, size));
    }

    // =========================
    // 2️⃣ TẠO MỚI
    // =========================
    @Operation(summary = "Tạo mới catalog")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).CATALOG_CREATE.value())")
    @PostMapping
    public ResponseEntity<CatalogRes> taoCatalog(
            @RequestBody CatalogReq catalogReq) {

        return ResponseEntity.ok(catalogService.taoCatalog(catalogReq));
    }

    // =========================
    // 3️⃣ CẬP NHẬT
    // =========================
    @Operation(summary = "Cập nhật catalog theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).CATALOG_UPDATE.value())")
    @PutMapping("/{id}")
    public ResponseEntity<CatalogRes> suaCatalog(
            @PathVariable Long id,
            @RequestBody CatalogReq catalogReq) {

        return ResponseEntity.ok(catalogService.suaCatalog(id, catalogReq));
    }

    // =========================
    // 4️⃣ XOÁ
    // =========================
    @Operation(summary = "Xoá catalog theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).CATALOG_DELETE.value())")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaCatalog(@PathVariable Long id) {
        catalogService.xoaCatalog(id);
        return ResponseEntity.ok().build();
    }
}
