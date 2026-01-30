package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.BienTheReq;
import entier.person.sale.dto.req.SanPhamReq;
import entier.person.sale.dto.res.BienTheRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.SanPhamRes;
import entier.person.sale.service.SanPhamService;
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
@RequestMapping("/quan-tri/san-pham")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý sản phẩm dành cho Admin")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_READ.value())")
public class AdminSanPhamController {

    private final SanPhamService loaiSpService;


    // 1) Lấy danh sách  sản phẩm
    @Operation(summary = "Lấy danh sách sản phẩm (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<SanPhamRes>> layDsSanPham(
            @RequestParam(required = false) Long loaiSp,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(loaiSpService.layDsSanPham(loaiSp, search, page, size));
    }

    @Operation(summary = "Lấy sản phẩm")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy thành công")
    @GetMapping("/{slug}")
    public ResponseEntity<SanPhamRes> laySanPham(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(loaiSpService.laySanPham(slug));
    }

    // 2) Tạo mới sản phẩm
    @Operation(summary = "Tạo mới sản phẩm")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_CREATE.value())")
    public ResponseEntity<SanPhamRes> taoSanPham(@RequestBody SanPhamReq loaiSpReq) {
        return ResponseEntity.ok(loaiSpService.taoSanPham(loaiSpReq));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_UPDATE.value())")
    // 3) Cập nhật sản phẩm
    @Operation(summary = "Cập nhật sản phẩm theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PutMapping("/{id}")
    public ResponseEntity<SanPhamRes> suaSanPham(@PathVariable Long id, @RequestBody SanPhamReq loaiSpReq) {
        return ResponseEntity.ok(loaiSpService.suaSanPham(id, loaiSpReq));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_DELETE.value())")
    // 4) Xoá sản phẩm
    @Operation(summary = "Xoá sản phẩm theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaSanPham(@PathVariable Long id) {
        loaiSpService.xoaSanPham(id);
        return ResponseEntity.ok().build();
    }

    // 2) Tạo mới biến thể
    @Operation(summary = "Tạo mới biến thể")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping("/{spId}/bien-the")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_READ.value())")
    public ResponseEntity<BienTheRes> taoBienThe(@PathVariable Long spId, @RequestBody BienTheReq bienTheReq) {
        return ResponseEntity.ok(loaiSpService.themBienThe(spId, bienTheReq));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_READ.value())")
    // 3) Cập nhật biến thể
    @Operation(summary = "Cập nhật biến thể theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PutMapping("/{spId}/bien-the/{id}")
    public ResponseEntity<BienTheRes> suaBienThe(@PathVariable Long id, @PathVariable Long spId, @RequestBody BienTheReq bienTheReq) {
        return ResponseEntity.ok(loaiSpService.suaBienthe(id, spId, bienTheReq));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).PRODUCT_READ.value())")
    // 4) Xoá biến thể
    @Operation(summary = "Xoá biến thể theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @DeleteMapping("/{spId}/bien-the/{id}")
    public ResponseEntity<?> xoaBienThe(@PathVariable Long spId, @PathVariable Long id) {
        loaiSpService.xoaBienThe(id, spId);
        return ResponseEntity.ok().build();
    }
}

