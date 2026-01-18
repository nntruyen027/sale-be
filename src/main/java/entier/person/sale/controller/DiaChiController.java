package entier.person.sale.controller;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.DiaChiReq;
import entier.person.sale.dto.res.DiaChiRes;
import entier.person.sale.service.AuthService;
import entier.person.sale.service.DiaChiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dia-chi")
@Getter
@Setter
@AllArgsConstructor
@Tag(name = "Địa chỉ", description = "Quản lý địa chỉ cá nhân")
public class DiaChiController {
    private final AuthService authService;
    private final DiaChiService diaChiService;

    @Operation(
            summary = "Lấy danh sách địa chỉ cá nhân",
            description = "API trả về danh sách địa chỉ cá nhân."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
    })
    @SecurityApiResponses
    @GetMapping
    public List<DiaChiRes> layDsDiaChiCaNhan() {
        return diaChiService.layDsDiaChiCaNhan(authService.getCurrentUserDto().getId());
    }

    @Operation(
            summary = "Thêm địa chỉ cá nhân"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Thêm thành công"),
    })
    @SecurityApiResponses
    @PostMapping
    public DiaChiRes themDiaChiCaNhan(@RequestBody DiaChiReq diaChiReq) {
        return diaChiService.themDiaChiCaNhan(authService.getCurrentUserDto().getId(), diaChiReq);
    }

    @Operation(
            summary = "Sửa địa chỉ cá nhân"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sửa thành công"),
    })
    @SecurityApiResponses
    @PutMapping
    public DiaChiRes suaDiaChiCaNhan(Long id, @RequestBody DiaChiReq diaChiReq) {
        return diaChiService.suaDiaChiCaNhan(id, authService.getCurrentUserDto().getId(), diaChiReq);
    }

    @Operation(
            summary = "Xóa địa chỉ cá nhân"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
    })
    @SecurityApiResponses
    @DeleteMapping
    public void xoaDiaChiCaNhan(Long id) {
        diaChiService.xoaDiaChiCaNhan(id, authService.getCurrentUserDto().getId());
    }
}
