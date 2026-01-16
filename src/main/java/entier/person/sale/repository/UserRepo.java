package entier.person.sale.repository;

import entier.person.sale.dto.req.RegiserReq;
import entier.person.sale.dto.res.UserAuthRes;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepo {

    private final DbFunctionExecutor dbFunctionExecutor;

    /**
     * Tìm user theo username (full info)
     */
    public Optional<UserFullRes> findByUsername(String username) {
        UserFullRes user = dbFunctionExecutor.execute(
                "auth.fn_lay_nguoi_dung_theo_username",
                List.of(username),
                UserFullRes.class);
        System.out.println(user);
        return Optional.ofNullable(user);
    }

    /**
     * Lấy thông tin auth theo username
     */
    public Optional<UserAuthRes> findAuthByUsername(String username) {
        UserAuthRes auth = dbFunctionExecutor.execute(
                "auth.fn_lay_thong_tin_auth_theo_username",
                List.of(username),
                UserAuthRes.class);
        return Optional.ofNullable(auth);
    }

    /**
     * Lấy thông tin auth theo id
     */
    public UserAuthRes findAuthById(Long id) {
        return dbFunctionExecutor.execute(
                "auth.fn_lay_thong_tin_auth_theo_id",
                List.of(id),
                UserAuthRes.class);
    }

    /**
     * Đổi mật khẩu
     */
    public boolean doiMatKhau(Long userId, String newPass) {
        Boolean result = dbFunctionExecutor.execute(
                "auth.fn_doi_mat_khau",
                List.of(userId, newPass),
                Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Tìm user theo id
     */
    public UserFullRes findById(Long userId) {
        return dbFunctionExecutor.execute(
                "auth.fn_lay_nguoi_dung_theo_id",
                List.of(userId),
                UserFullRes.class);
    }

    /**
     * Phân vai trò cho người dùng
     * roles phải là JSON ARRAY trong DB
     */
    public UserFullRes phanVaiTroChoNguoiDung(Long id, List<String> roles) {

        return dbFunctionExecutor.execute(
                "auth.fn_phan_vai_tro_cho_nguoi_dung",
                List.of(id, roles.toArray(new String[0])),
                UserFullRes.class);
    }

    public UserFullRes dangKy(RegiserReq req) {
        List<Object> params = new ArrayList<>();
        params.add(req.getUsername());
        params.add((req.getPassword()));
        params.add(req.getHoTen());
        params.add(req.getEmail());


        return dbFunctionExecutor.execute(
                "auth.fn_dang_ky",
                params,
                UserFullRes.class
        );
    }


}
