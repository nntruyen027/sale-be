package entier.person.sale.controller;

import entier.person.sale.dto.req.LoginReq;
import entier.person.sale.dto.req.UpdatePassReq;
import entier.person.sale.dto.res.LoginRes;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.exception.AppException;
import entier.person.sale.repository.UserRepo;
import entier.person.sale.service.AuthService;
import entier.person.sale.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Getter
@Setter
@AllArgsConstructor
@Tag(name = "Xác thực & Tài khoản", description = "Quản lý đăng nhập, đăng ký giáo viên và thông tin người dùng")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private AuthService authService;

    // -----------------------------------------------------------
    // LOGIN
    // -----------------------------------------------------------
    @Operation(
            summary = "Đăng nhập hệ thống",
            description = "Nhập username và password để lấy JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công, trả về token",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Sai username hoặc password", content = @Content)
    })
    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String token = jwtService.generateToken(request.getUsername());
            UserFullRes user = (userRepository.findByUsername(request.getUsername()).get());
            return new LoginRes(token, user);

        } catch (BadCredentialsException ex) {
            throw new AppException("BAD_CREDENTIAL", "Thông tin đăng nhập không hợp lệ");
        }
    }


    // -----------------------------------------------------------
    // LẤY THÔNG TIN NGƯỜI DÙNG HIỆN TẠI
    // -----------------------------------------------------------
    @Operation(
            summary = "Xem thông tin user hiện tại",
            description = "Trả về thông tin user đang đăng nhập.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "401", description = "Không có hoặc token không hợp lệ", content = @Content)
    })
    @SecurityRequirement(name = "BearerAuth")
    @GetMapping("/me")
    public ResponseEntity<UserFullRes> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUserDto());
    }

    // -----------------------------------------------------------
    // ĐỔI MẬT KHẨU
    // -----------------------------------------------------------
    @Operation(
            summary = "Đổi mật khẩu",
            description = "Yêu cầu người dùng phải đăng nhập.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Đổi mật khẩu thành công"),
            @ApiResponse(responseCode = "400", description = "Mật khẩu cũ không đúng", content = @Content),
            @ApiResponse(responseCode = "401", description = "Không có hoặc token không hợp lệ", content = @Content)
    })
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/doi-mat-khau")
    public ResponseEntity<Void> doiMatKhau(@RequestBody UpdatePassReq updatePassReq) {
        authService.doiMatKhau(updatePassReq);
        return ResponseEntity.ok().build();
    }

}


