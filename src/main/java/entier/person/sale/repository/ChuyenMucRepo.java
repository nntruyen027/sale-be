package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.ChuyenMucReq;
import entier.person.sale.dto.res.ChuyenMucRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ChuyenMucRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<ChuyenMucRes> layDsChuyenMuc(String search, int page, int size) {
        List<Object> params = new ArrayList<>();
        params.add(search);
        params.add(page);
        params.add(size);
        return dbFunctionExecutor.execute(
                "blog.fn_lay_ds_chuyen_muc",
                params,
                new TypeReference<PageResponse<ChuyenMucRes>>() {

                }
        );
    }

    public ChuyenMucRes taoChuyenMuc(ChuyenMucReq chuyeMucReq) {
        List<Object> params = new ArrayList<>();
        params.add(chuyeMucReq.getTen());
        params.add(chuyeMucReq.getParentId());
        params.add(chuyeMucReq.getSlug());
        return dbFunctionExecutor.execute(
                "blog.fn_them_chuyen_muc",
                params,
                ChuyenMucRes.class
        );
    }

    public ChuyenMucRes suaChuyenMuc(Long id, ChuyenMucReq chuyeMucReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(chuyeMucReq.getTen());
        params.add(chuyeMucReq.getParentId());
        params.add(chuyeMucReq.getSlug());
        return dbFunctionExecutor.execute(
                "blog.fn_sua_chuyen_muc",
                params,
                ChuyenMucRes.class
        );
    }

    public void xoaChuyenMuc(Long id) {
        dbFunctionExecutor.execute(
                "blog.fn_xoa_chuyen_muc",
                List.of(id),
                Boolean.class
        );
        
    }
}
