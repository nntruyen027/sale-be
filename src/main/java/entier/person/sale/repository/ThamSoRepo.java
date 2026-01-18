package entier.person.sale.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.ParamReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.ThamSoRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ThamSoRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<ThamSoRes> layDsThamSo(String search, int page, int size) {
        List<Object> params = new ArrayList<>();
        params.add(search);
        params.add(page);
        params.add(size);

        return dbFunctionExecutor.execute(
                "fn_lay_ds_tham_so",
                params,
                new TypeReference<PageResponse<ThamSoRes>>() {
                }
        );
    }

    public String layThamSo(String khoa) {
        List<Object> params = new ArrayList<>();
        params.add(khoa);

        return dbFunctionExecutor.execute(
                "fn_lay_tham_so",
                params,
                String.class
        );
    }

    public ThamSoRes taoThamSo(ParamReq tinhReq) {
        List<Object> params = new ArrayList<>();
        params.add(tinhReq.getKhoa());
        params.add(tinhReq.getKieuDuLieu());
        params.add(tinhReq.getGiaTri());

        return dbFunctionExecutor.execute(
                "fn_them_tham_so",
                params,
                ThamSoRes.class
        );
    }

    public ThamSoRes suaThamSo(Long id, ParamReq tinhReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(tinhReq.getKhoa());
        params.add(tinhReq.getKieuDuLieu());
        params.add(tinhReq.getGiaTri());
        params.add(tinhReq.getIsEnable());

        return dbFunctionExecutor.execute(
                "fn_sua_tham_so",
                params,
                ThamSoRes.class
        );
    }

    public void xoaThamSo(Long id) {
        dbFunctionExecutor.execute(
                "fn_xoa_tham_so",
                List.of(id),
                Void.class
        );
    }
}
