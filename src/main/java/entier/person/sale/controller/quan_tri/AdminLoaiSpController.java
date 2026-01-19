package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.HasPermission;
import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.constant.QuyenCons;
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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/quan-tri/loai-san-pham")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý loại sản phẩm dành cho Admin")
@HasPermission(permission = QuyenCons.PTYPE_READ)
public class AdminLoaiSpController {

    private final LoaiSpService loaiSpService;


    // 1) Lấy danh sách loại  sản phẩm
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

    // 2) Tạo mới sản phẩm
    @Operation(summary = "Tạo mới loại sản phẩm")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping
    @HasPermission(permission = QuyenCons.PTYPE_CREATE)
    public ResponseEntity<LoaiSpRes> taoLoaiSp(@RequestBody LoaiSpReq loaiSpReq) {
        return ResponseEntity.ok(loaiSpService.taoLoaiSp(loaiSpReq));
    }

    @HasPermission(permission = QuyenCons.PTYPE_UPDATE)
    // 3) Cập nhật sản phẩm
    @Operation(summary = "Cập nhật loại sản phẩm theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PutMapping("/{id}")
    public ResponseEntity<LoaiSpRes> suaLoaiSp(@PathVariable Long id, @RequestBody LoaiSpReq loaiSpReq) {
        return ResponseEntity.ok(loaiSpService.suaLoaiSp(id, loaiSpReq));
    }

    @HasPermission(permission = QuyenCons.PTYPE_DELETE)
    // 4) Xoá sản phẩm
    @Operation(summary = "Xoá loại sản phẩm theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaLoaiSp(@PathVariable Long id) {
        loaiSpService.xoaLoaiSp(id);
        return ResponseEntity.ok().build();
    }
}

