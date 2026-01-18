package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.DiaChiReq;
import entier.person.sale.dto.res.DiaChiRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class DiaChiRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public List<DiaChiRes> layDsDiaChiCaNhan(Long userId) {
        return dbFunctionExecutor.execute(
                "auth.fn_lay_ds_dia_chi_ca_nhan",
                List.of(userId),
                new TypeReference<List<DiaChiRes>>() {
                }
        );
    }

    public DiaChiRes themDiaChiCaNhan(Long userId, DiaChiReq diaChiReq) {
        List<Object> params = new ArrayList<>();
        params.add(diaChiReq.getXaId());
        params.add(diaChiReq.getChiTiet());
        params.add(userId);
        params.add(diaChiReq.getDinhVi());
        params.add(diaChiReq.getIsDefault());

        return dbFunctionExecutor.execute(
                "auth.fn_them_dia_chi_ca_nhan",
                params,
                DiaChiRes.class
        );
    }

    public DiaChiRes suaDiaChiCaNhan(Long id, Long userId, DiaChiReq diaChiReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(diaChiReq.getXaId());
        params.add(diaChiReq.getChiTiet());
        params.add(userId);
        params.add(diaChiReq.getDinhVi());
        params.add(diaChiReq.getIsDefault());


        return dbFunctionExecutor.execute(
                "auth.fn_sua_dia_chi_ca_nhan",
                params,
                DiaChiRes.class
        );
    }

    public void xoaDiaChiCaNhan(Long id, Long userId) {
        dbFunctionExecutor.execute(
                "auth.fn_xoa_dia_chi_ca_nhan",
                List.of(id, userId),
                Void.class
        );
    }
}
