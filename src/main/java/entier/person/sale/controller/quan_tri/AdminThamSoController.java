package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.req.ParamReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.ThamSoRes;
import entier.person.sale.service.ThamSoService;
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
@RequestMapping("/quan-tri/tham-so")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản trị tham số dành cho quản trị viên")
@PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PARAM_READ)")
public class AdminThamSoController {
    private final ThamSoService thamSoService;


    // 1) Lấy danh sách tham số
    @Operation(summary = "Lấy danh sách tham số (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<ThamSoRes>> layDsThamSo(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(thamSoService.layDsThamSo(search, page, size));
    }

    // 2) Tạo mới tham số
    @Operation(summary = "Tạo mới tham số")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping
    @PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PARAM_CREATE)")
    public ResponseEntity<ThamSoRes> taoThamSo(@RequestBody ParamReq tinhReq) {
        return ResponseEntity.ok(thamSoService.taoThamSo(tinhReq));
    }

    @PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PARAM_UPDATE)")    // 3) Cập nhật tham số
    @Operation(summary = "Cập nhật tham số theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PutMapping("/{id}")
    public ResponseEntity<ThamSoRes> suaThamSo(@PathVariable Long id, @RequestBody ParamReq tinhReq) {
        return ResponseEntity.ok(thamSoService.suaThamSo(id, tinhReq));
    }

    @PreAuthorize("@perm.has(T(entier.person.sale.constant.QuyenCons).PARAM_DELETE)")    // 4) Xoá tham số
    @Operation(summary = "Xoá tham số theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaThamSo(@PathVariable Long id) {
        thamSoService.xoaThamSo(id);
        return ResponseEntity.ok().build();
    }


}
