package entier.person.sale.controller.cong_khai;


import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.dto.res.LoaiSpRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.TinhRes;
import entier.person.sale.dto.res.XaRes;
import entier.person.sale.service.LoaiSpService;
import entier.person.sale.service.ThamSoService;
import entier.person.sale.service.TinhService;
import entier.person.sale.service.XaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cong-khai")
@Getter
@Setter
@AllArgsConstructor
@Tag(name = "Công khai")
public class PublicController {
    private final XaService xaService;
    private final TinhService tinhService;
    private final ThamSoService thamSoService;
    private final LoaiSpService loaiSpService;


    // 1) Lấy danh sách tỉnh
    @Operation(summary = "Lấy danh sách tỉnh (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/tinh")
    public ResponseEntity<PageResponse<TinhRes>> layDsTinh(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(tinhService.layDsTinh(search, page, size));
    }

    // ============================
    // 1. Danh sách xã
    // ============================
    @Operation(
            summary = "Lấy danh sách xã",
            description = "API trả về danh sách xã theo phân trang, có hỗ trợ tìm kiếm và lọc theo tỉnh."
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/tinh/{tinhId}/xaId")
    public PageResponse<XaRes> layDsXa(
            @PathVariable Long tinhId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return xaService.layDsXa(search, tinhId, page, size);
    }

    // 1) Lấy danh sách tham số
    @Operation(summary = "Lấy tham số theo khóa")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/tham-so/{khoa}")
    public ResponseEntity<String> layThamSoTheoKhoa(
            @PathVariable String khoa) {

        return ResponseEntity.ok(thamSoService.layThamSo(khoa));
    }

    // 1) Lấy danh sách loại  sản phẩm
    @Operation(summary = "Lấy danh sách loại sản phẩm (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/loai-san-pham")
    public ResponseEntity<PageResponse<LoaiSpRes>> layDsLoaiSp(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(loaiSpService.layDsLoaiSp(search, page, size));
    }

}
