package entier.person.sale.controller.quan_tri;

import entier.person.sale.config.SecurityApiResponses;
import entier.person.sale.service.HeThongService;
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
@RequestMapping("/quan-tri/he-thong")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý hệ thống dành cho Admin")
@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
public class AdminHeThongController {

    private final HeThongService heThongService;


    // =========================
    // 4️⃣ ài đặt phần cấu hình
    // =========================
    @Operation(summary = "Cài đặt phần cấu hình")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cài đặt thành công", content = @Content)
    @PostMapping("/{cauHinh}")
    public ResponseEntity<?> SetUpBannerGioiThieu(@PathVariable String cauHinh, @RequestBody String object) {
        return ResponseEntity.ok(heThongService.SetUpCauHinh(object, cauHinh));
    }

    // =========================
    // 4️⃣ ài đặt phần cấu hình
    // =========================
    @Operation(summary = "Lấy phần cấu hình")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy thành công", content = @Content)
    @GetMapping("/{cauHinh}")
    public ResponseEntity<?> SetUpBannerGioiThieu(@PathVariable String cauHinh) {
        return ResponseEntity.ok(heThongService.layCauHinh(cauHinh));
    }
}
