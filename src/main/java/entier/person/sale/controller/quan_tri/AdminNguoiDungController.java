package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.PhanQuyenReq;
import entier.person.sale.dto.req.RegiserReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.service.NguoiDungService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/nguoi-dung")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản trị người dùng (Admin)")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).USER_READ.value())")
public class AdminNguoiDungController {

    private final NguoiDungService nguoiDungService;

    // ====================== LẤY DANH SÁCH ======================
    @Operation(
            summary = "Lấy danh sách người dùng",
            description = "Trả về danh sách người dùng theo phân trang và tìm kiếm"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    })
    @SecurityApiResponses
    @GetMapping
    public ResponseEntity<PageResponse<UserFullRes>> layDsNguoiDung(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
                nguoiDungService.layTatCaNguoiDung(search, page, limit)
        );
    }

    // ====================== TẠO NGƯỜI DÙNG ======================
    @Operation(
            summary = "Tạo người dùng",
            description = "Tạo mới một người dùng"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo thành công")
    })
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).USER_CREATE.value())")
    @PostMapping
    public ResponseEntity<UserFullRes> taoNguoiDung(
            @RequestBody RegiserReq req
    ) {
        return ResponseEntity.ok(
                nguoiDungService.taoNguoiDung(req)
        );
    }

    // ====================== CẬP NHẬT NGƯỜI DÙNG ======================
    @Operation(
            summary = "Cập nhật người dùng",
            description = "Cập nhật thông tin người dùng theo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    })
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).USER_UPDATE.value())")
    @PutMapping("/{id}")
    public ResponseEntity<UserFullRes> suaNguoiDung(
            @PathVariable Long id,
            @RequestBody RegiserReq req
    ) {
        return ResponseEntity.ok(
                nguoiDungService.suaNguoiDung(id, req)
        );
    }

    // ====================== PHÂN QUYỀN ======================
    @Operation(
            summary = "Phân quyền cho người dùng",
            description = "Gán danh sách quyền cho người dùng"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Phân quyền thành công")
    })
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).USER_UPDATE.value())")
    @PutMapping("/{id}/quyen")
    public ResponseEntity<UserFullRes> phanQuyenNguoiDung(
            @PathVariable Long id,
            @RequestBody PhanQuyenReq req
    ) {
        return ResponseEntity.ok(
                nguoiDungService.phanVaiTroChoNguoiDung(id, req.getDsMaQuyen())
        );
    }

    // ====================== XOÁ NGƯỜI DÙNG ======================
    @Operation(
            summary = "Xóa người dùng",
            description = "Xóa người dùng theo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công")
    })
    @SecurityApiResponses
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority(T(entier.person.sale.constant.QuyenCons).USER_DELETE.value())")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaNguoiDung(@PathVariable Long id) {
        nguoiDungService.xoaNguoiDung(id);
        return ResponseEntity.ok().build();
    }
}
