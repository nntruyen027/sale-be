package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.LoaiSpReq;
import entier.person.sale.dto.res.LoaiSpRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class LoaiSpRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<LoaiSpRes> layDsLoaiSp(String search, int page, int size) {
        List<Object> params = new ArrayList<>();
        params.add(search);
        params.add(page);
        params.add(size);
        return dbFunctionExecutor.execute(
                "product.fn_lay_ds_loai",
                params,
                new TypeReference<PageResponse<LoaiSpRes>>() {

                }
        );
    }

    public LoaiSpRes taoLoaiSp(LoaiSpReq loaiSpReq) {
        List<Object> params = new ArrayList<>();
        params.add(loaiSpReq.getTen());
        params.add(loaiSpReq.getParentId());
        params.add(loaiSpReq.getHinhAnh());
        params.add(loaiSpReq.getSlug());
        return dbFunctionExecutor.execute(
                "product.fn_them_loai",
                params,
                LoaiSpRes.class
        );
    }

    public LoaiSpRes suaLoaiSp(Long id, LoaiSpReq loaiSpReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(loaiSpReq.getTen());
        params.add(loaiSpReq.getParentId());
        params.add(loaiSpReq.getHinhAnh());
        params.add(loaiSpReq.getSlug());
        return dbFunctionExecutor.execute(
                "product.fn_sua_loai",
                params,
                LoaiSpRes.class
        );
    }

    public void xoaLoaiSp(Long id) {
        dbFunctionExecutor.execute(
                "product.fn_xoa_loai",
                List.of(id),
                Boolean.class
        );
    }
}
