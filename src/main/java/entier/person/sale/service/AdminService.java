package entier.person.sale.service;

import entier.person.sale.dto.req.AdminRequest;
import entier.person.sale.dto.req.UpdateAdminReq;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.repository.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepo adminRepo;
    private final AuthService authService;

    public UserFullRes taoQuanTriVien(AdminRequest adminRequest) {
        return adminRepo.taoQuanTriVien(adminRequest);
    }

    public UserFullRes suaQuanTriVien(UpdateAdminReq adminRequest) {
        return adminRepo.capNhatQuanTriVien(authService.getCurrentUser().getId(), adminRequest);
    }
}
