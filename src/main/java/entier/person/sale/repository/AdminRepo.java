package entier.person.sale.repository;

import entier.person.sale.dto.req.AdminRequest;
import entier.person.sale.dto.req.UpdateAdminReq;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.util.DbFunctionExecutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminRepo {
        private final PasswordEncoder passwordEncoder;
        private final DbFunctionExecutor dbFunctionExecutor;

        public UserFullRes taoQuanTriVien(AdminRequest adminRequest) {
                List<Object> params = new ArrayList<>();
                params.add(adminRequest.getUsername());
                params.add(adminRequest.getHoTen());
                params.add(passwordEncoder.encode(adminRequest.getPassword()));
                params.add(adminRequest.getAvatar());


            return dbFunctionExecutor.execute(
                            "auth.fn_tao_nguoi_dung_quan_tri",
                            params ,
                            UserFullRes.class);
        }

        public UserFullRes capNhatQuanTriVien(Long id, UpdateAdminReq adminRequest) {

                UserFullRes pro = dbFunctionExecutor.execute(
                                "auth.fn_cap_nhat_thong_tin_quan_tri_vien",
                                List.of(
                                                id,
                                                adminRequest.getAvatar(),
                                                adminRequest.getHoTen()),
                                UserFullRes.class);

                return pro;
        }
}
