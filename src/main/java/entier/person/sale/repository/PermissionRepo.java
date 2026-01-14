package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.PermissionRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PermissionRepo {

    private final DbFunctionExecutor dbFunctionExecutor;

    /**
     * Trả về SET permission code theo userId
     */
    public Set<String> timPermissionTheoUserId(Long userId) {

        List<String> perms = dbFunctionExecutor.execute(
                "auth.fn_lay_permission_theo_user",
                List.of(userId),
                new TypeReference<List<String>>() {
                });

        return perms == null
                ? Set.of()
                : perms.stream().collect(Collectors.toSet());
    }

    /**
     * Tạo permission mới
     */
    public PermissionRes taoPermission(String code) {
        return dbFunctionExecutor.execute(
                "auth.fn_tao_permission",
                List.of(code),
                PermissionRes.class);
    }

    /**
     * Kiểm tra permission tồn tại
     */
    public Boolean coPermission(String code) {
        Boolean exists = dbFunctionExecutor.execute(
                "auth.fn_kiem_tra_ton_tai_permission_theo_code",
                List.of(code),
                Boolean.class);
        return Boolean.TRUE.equals(exists);
    }

    /**
     * Lấy danh sách quyền (phân trang)
     */
    public PageResponse<PermissionRes> layTatCaQuyen(String search, int page, int size) {

        return dbFunctionExecutor.execute(
                "auth.fn_lay_tat_ca_quyen",
                List.of(search, page, size),
                new TypeReference<PageResponse<PermissionRes>>() {
                });
    }

}
