package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.CatalogReq;
import entier.person.sale.dto.res.CatalogRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CatalogRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<CatalogRes> layDsCatalog(String search, int page, int size) {
        List<Object> params = new ArrayList<>();
        params.add(search);
        params.add(page);
        params.add(size);
        return dbFunctionExecutor.execute(
                "blog.fn_lay_ds_catalog",
                params,
                new TypeReference<PageResponse<CatalogRes>>() {

                }
        );
    }

    public CatalogRes taoCatalog(CatalogReq chuyeMucReq) {
        List<Object> params = new ArrayList<>();
        params.add(chuyeMucReq.getTieuDe());
        params.add(chuyeMucReq.getUrl());
        return dbFunctionExecutor.execute(
                "blog.fn_them_catalog",
                params,
                CatalogRes.class
        );
    }

    public CatalogRes suaCatalog(Long id, CatalogReq chuyeMucReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(chuyeMucReq.getTieuDe());
        params.add(chuyeMucReq.getUrl());
        return dbFunctionExecutor.execute(
                "blog.fn_sua_catalog",
                params,
                CatalogRes.class
        );
    }

    public void xoaCatalog(Long id) {
        dbFunctionExecutor.execute(
                "blog.fn_xoa_catalog",
                List.of(id),
                Boolean.class
        );

    }
}
