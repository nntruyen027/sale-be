package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.HasPermission;
import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.constant.QuyenCons;
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
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/nguoi-dung")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản trị người dùng dành cho quản trị viên")
@HasPermission(permission = QuyenCons.USER_READ)
public class AdminNguoiDungController {
    private final NguoiDungService nguoiDungService;


    @Operation(
            summary = "Lấy danh sách người dùng",
            description = "API trả về danh sách người dùng theo phân trang và tìm kiếm theo tên."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
    })
    @SecurityApiResponses
    @GetMapping("")
    public PageResponse<UserFullRes> layDsNguoiDung(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return nguoiDungService.layTatCaNguoiDung(search, page, limit);
    }

    @Operation(
            summary = "Tạo người dùng",
            description = "API tạo người dùng."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo thành công"),
    })
    @SecurityApiResponses
    @PostMapping("")
    @HasPermission(permission = QuyenCons.USER_CREATE)
    public UserFullRes taoNguoiDung(@RequestBody RegiserReq nguoiDungReq) {

        return nguoiDungService.taoNguoiDung(nguoiDungReq);
    }

    @Operation(
            summary = "Sửa người dùng",
            description = "API sửa người dùng."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sửa thành công"),
    })
    @SecurityApiResponses
    @PutMapping("/{id}")
    @HasPermission(permission = QuyenCons.USER_UPDATE)
    public UserFullRes suaNguoiDung(@PathVariable Long id, @RequestBody RegiserReq nguoiDungReq) {
        return nguoiDungService.suaNguoiDung(id, nguoiDungReq);
    }


    @Operation(
            summary = "Phần quyền cho người dùng",
            description = "API phân quyền cho người dùng."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Phân quyền thành công"),
    })
    @SecurityApiResponses
    @PutMapping("/{id}/quyen")
    @HasPermission(permission = QuyenCons.USER_UPDATE)
    public UserFullRes phanVaiTroChoNguoiDung(@PathVariable Long id, @RequestBody PhanQuyenReq phanQuyenReq) {
        return nguoiDungService.phanVaiTroChoNguoiDung(id, phanQuyenReq.getDsMaQuyen());
    }


    @Operation(
            summary = "Xóa người dùng",
            description = "API xóa người dùng."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
    })
    @SecurityApiResponses
    @DeleteMapping("/{id}")
    @HasPermission(permission = QuyenCons.USER_DELETE)
    public void xoaNguoiDung(@PathVariable Long id) {
        nguoiDungService.xoaNguoiDung(id);
    }
}
