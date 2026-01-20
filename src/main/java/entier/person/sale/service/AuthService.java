package entier.person.sale.service;

import entier.person.sale.dto.req.RegiserReq;
import entier.person.sale.dto.req.UpdatePassReq;
import entier.person.sale.dto.res.UserAuthRes;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.exception.AppException;
import entier.person.sale.repository.UserRepo;
import entier.person.sale.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserFullRes getCurrentUserDto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Người dùng chưa được xác thực");
        }

        return userRepo.findByUsername(auth.getName()).get();
    }

    public UserFullRes getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Người dùng chưa được xác thực");
        }

        return userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));
    }

    public void doiMatKhau(UpdatePassReq updatePassReq) {
        String username = SecurityUtils.getCurrentUsername();

        if (username == null) {
            throw new AppException("USER_NOT_FOUND", "Không tìm thấy thông tin người dùng đăng nhập");
        }

        UserAuthRes user = userRepo.findAuthByUsername(username)
                .orElseThrow(() -> new AppException("USER_NOT_FOUND", "Không tìm thấy người dùng"));

        if (updatePassReq.getOldPass() == null || updatePassReq.getOldPass().isBlank()) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu cũ không được để trống");
        }

        if (!passwordEncoder.matches(updatePassReq.getOldPass(), user.getPassword())) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu cũ không chính xác");
        }

        if (updatePassReq.getNewPass() == null || updatePassReq.getNewPass().isBlank()) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu mới không được để trống");
        }

        if (!updatePassReq.getNewPass().equals(updatePassReq.getRepeatNewPass())) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu mới không khớp");
        }

        userRepo.doiMatKhau(user.getId(), passwordEncoder.encode(updatePassReq.getNewPass()));
    }

    public void doiMatKhau(Long userId, UpdatePassReq updatePassReq) {
        UserAuthRes user = userRepo.findAuthById(userId);

        if (!passwordEncoder.matches(updatePassReq.getOldPass(), user.getPassword())) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu cũ không chính xác");
        }

        if (updatePassReq.getNewPass() != updatePassReq.getRepeatNewPass()) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu mới không khớp");
        }

        userRepo.doiMatKhau(user.getId(), passwordEncoder.encode(updatePassReq.getNewPass()));
    }

    public void datLaiMatKhauBoiAdmin(Long userId, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu mới không được để trống");
        }

        UserAuthRes user = userRepo.findAuthById(userId);

        userRepo.doiMatKhau(user.getId(), passwordEncoder.encode(newPassword));
    }

    public UserFullRes dangKy(RegiserReq request) {
        if (!(request.getPassword() == null || request.getPassword().isBlank()))
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepo.dangKy(request);
    }

}
