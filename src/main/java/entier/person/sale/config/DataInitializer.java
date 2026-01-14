package entier.person.sale.config;

import entier.person.sale.dto.req.AdminRequest;
import entier.person.sale.dto.req.RoleReq;
import entier.person.sale.dto.res.RoleRes;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.repository.PermissionRepo;
import entier.person.sale.repository.RoleRepo;
import entier.person.sale.repository.UserRepo;
import entier.person.sale.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

@Configuration
@AllArgsConstructor
@Profile("!test")
public class DataInitializer {

    private static final String SUPER_ADMIN_CODE = "SUPER_ADMIN";
    private static final String ADMIN_CODE = "ADMIN";
    private static final String SUPER_ADMIN_USERNAME = "superadmin";
    private static final String DEFAULT_PASSWORD = "Sale@2025";
    private static final List<String> DEFAULT_PERMISSIONS = List.of(
            "ADMIN_MANAGE",
            "USER_MANAGE",
            "ROLE_MANAGE",
            "SYSTEM_CONFIG",
            "CATEGORY_MANAGE");

    @Bean
    public CommandLineRunner initAdmin(
            UserRepo userRepo,
            AdminService adminService,
            PermissionRepo permissionRepo,
            RoleRepo roleRepo) {
        return args -> {

            // 1️⃣ Tạo permission nếu chưa có
            DEFAULT_PERMISSIONS.forEach(code -> {
                if (!permissionRepo.coPermission(code)) {
                    permissionRepo.taoPermission(code);
                }
            });

            // 2️⃣ Tạo role SUPER_ADMIN nếu chưa có
            if (!roleRepo.coVaiTroTheoMa(SUPER_ADMIN_CODE)) {
                roleRepo.taoVaiTro(new RoleReq("Quản trị tối cao", SUPER_ADMIN_CODE));
            }

            if (!roleRepo.coVaiTroTheoMa(ADMIN_CODE)) {
                roleRepo.taoVaiTro(new RoleReq("Quản trị viên", ADMIN_CODE));
            }

            // 3️⃣ Phân quyền cho role SUPER_ADMIN
            RoleRes superAdminRole = roleRepo.timVaiTroTheoMa(SUPER_ADMIN_CODE);
            System.out.println(superAdminRole.toString());
            roleRepo.phanQuyenChoVaiTro(superAdminRole.getId(), DEFAULT_PERMISSIONS);

            // 4️⃣ Tạo user SUPER_ADMIN nếu chưa có
            Optional<UserFullRes> userOpt = userRepo.findByUsername(SUPER_ADMIN_USERNAME);
            Long userId = userOpt.map(UserFullRes::getId).orElseGet(() -> {
                AdminRequest adminRequest = new AdminRequest();
                adminRequest.setUsername(SUPER_ADMIN_USERNAME);
                adminRequest.setHoTen("Quản trị viên");
                adminRequest.setPassword(DEFAULT_PASSWORD);
                adminRequest.setRepeatPassword(DEFAULT_PASSWORD);

                return adminService.taoQuanTriVien(adminRequest).getId();
            });

            // 5️⃣ Gán role SUPER_ADMIN cho user
            userRepo.phanVaiTroChoNguoiDung(userId, List.of(SUPER_ADMIN_CODE, ADMIN_CODE));

            System.out.println("✅ SUPER_ADMIN initialized with all permissions.");
        };
    }
}
