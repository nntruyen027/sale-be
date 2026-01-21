package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.RoleReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.RoleRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RoleRepo {

    private final DbFunctionExecutor dbFunctionExecutor;

    /**
     * Lấy danh sách vai trò (phân trang)
     */
    public PageResponse<RoleRes> layTatCaVaiTro(String search, int page, int size) {

        return dbFunctionExecutor.execute(
                "auth.fn_lay_tat_ca_vai_tro",
                List.of(search, page, size),
                new TypeReference<PageResponse<RoleRes>>() {
                });
    }

    /**
     * Tạo vai trò
     */
    public RoleRes taoVaiTro(RoleReq roleReq) {
        return dbFunctionExecutor.execute(
                "auth.fn_tao_vai_tro",
                List.of(roleReq.getName(), roleReq.getCode()),
                RoleRes.class);
    }

    /**
     * Sửa vai trò
     */
    public RoleRes suaVaiTro(Long id, RoleReq roleReq) {
        return dbFunctionExecutor.execute(
                "auth.fn_sua_vai_tro",
                List.of(id, roleReq.getName(), roleReq.getCode()),
                RoleRes.class);
    }

    /**
     * Phân quyền cho vai trò
     * permissions phải là JSON ARRAY trong DB function
     */
    public RoleRes phanQuyenChoVaiTro(Long id, List<String> permissions) {

        return dbFunctionExecutor.execute(
                "auth.fn_phan_quyen_cho_vai_tro",
                List.of(id, permissions.toArray(new String[0])),
                RoleRes.class);

    }

    /**
     * Xóa vai trò
     */
    public void xoaVaiTro(Long id) {
        dbFunctionExecutor.execute(
                "auth.fn_xoa_vai_tro",
                List.of(id),
                Boolean.class);
    }

    /**
     * Người dùng có vai trò không
     */
    public boolean coVaiTro(Long userId, String roleCode) {
        Boolean result = dbFunctionExecutor.execute(
                "auth.fn_nguoi_dung_co_vai_tro",
                List.of(userId, roleCode),
                Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Kiểm tra tồn tại vai trò theo mã
     */
    public boolean coVaiTroTheoMa(String code) {
        Boolean result = dbFunctionExecutor.execute(
                "auth.fn_co_ton_tai_vai_tro_theo_ma",
                List.of(code),
                Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Tìm vai trò theo mã
     */
    public RoleRes timVaiTroTheoMa(String code) {
        return dbFunctionExecutor.execute(
                "auth.fn_lay_vai_tro_theo_ma",
                List.of(code),
                RoleRes.class);
    }
}
