package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.BannerReq;
import entier.person.sale.dto.res.BannerRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class HeThongRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public List<BannerRes> layDsBanner() {
        return dbFunctionExecutor.execute(
                "config.fn_lay_ds_banner",
                List.of(),
                new TypeReference<List<BannerRes>>() {

                });
    }

    public BannerRes taoBanner(BannerReq bannerReq) {
        List<Object> params = List.of();
        params.add(bannerReq.getHinhAnh());
        params.add(bannerReq.getUrl());
        params.add(bannerReq.getThuTu());
        params.add(bannerReq.getLaMacDinh());

        return dbFunctionExecutor.execute(
                "config.fn_them_banner",
                params,
                BannerRes.class);
    }

    public BannerRes suaBanner(Long id, BannerReq bannerReq) {
        List<Object> params = List.of();
        params.add(id);
        params.add(bannerReq.getHinhAnh());
        params.add(bannerReq.getUrl());
        params.add(bannerReq.getThuTu());
        params.add(bannerReq.getLaMacDinh());

        return dbFunctionExecutor.execute(
                "config.fn_sua_banner",
                params,
                BannerRes.class);
    }

    public void xoaBanner(Long id) {
        dbFunctionExecutor.execute(
                "config.fn_xoa_banner",
                List.of(id),
                Boolean.class);
    }
}
