package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.BienTheReq;
import entier.person.sale.dto.req.SanPhamReq;
import entier.person.sale.dto.res.BienTheRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.SanPhamRes;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class SanPhamRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<SanPhamRes> layDsSanPham(Long loaiId, String search, int page, int size) {
        List<Object> params = new ArrayList<>();
        params.add(loaiId);
        params.add(search);
        params.add(page);
        params.add(size);

        return dbFunctionExecutor.execute(
                "product.fn_lay_ds_san_pham",
                params,
                new TypeReference<PageResponse<SanPhamRes>>() {
                }
        );
    }

    public SanPhamRes taoSanPham(SanPhamReq sanPhamReq) {
        List<Object> params = new ArrayList<>();
        params.add(sanPhamReq.getLoaiId());
        params.add(sanPhamReq.getTen());
        params.add(sanPhamReq.getHinhAnh());
        params.add(sanPhamReq.getMoTa());
        return
                dbFunctionExecutor.execute(
                        "product.fn_them_san_pham",
                        params,
                        SanPhamRes.class
                );
    }

    public SanPhamRes suaSanPham(Long id, SanPhamReq sanPhamReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(sanPhamReq.getLoaiId());
        params.add(sanPhamReq.getTen());
        params.add(sanPhamReq.getHinhAnh());
        params.add(sanPhamReq.getMoTa());
        return
                dbFunctionExecutor.execute(
                        "product.fn_sua_san_pham",
                        params,
                        SanPhamRes.class
                );
    }

    public void xoaSanPham(Long id) {
        dbFunctionExecutor.execute(
                "product.fn_xoa_san_pham",
                List.of(id),
                Boolean.class
        );
    }

    public BienTheRes themBienThe(Long spId, BienTheReq bienTheReq) {
        List<Object> params = new ArrayList<>();
        params.add(spId);
        params.add(bienTheReq.getSku());
        params.add(bienTheReq.getHinhAnh());
        params.add(bienTheReq.getMauSac());
        params.add(bienTheReq.getKichCo());
        params.add(bienTheReq.getGia());
        params.add(bienTheReq.getTonKho());

        return dbFunctionExecutor.execute(
                "product.fn_them_bien_the",
                params,
                BienTheRes.class
        );
    }

    public BienTheRes suaBienThe(Long id, Long spId, BienTheReq bienTheReq) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(spId);
        params.add(bienTheReq.getSku());
        params.add(bienTheReq.getHinhAnh());
        params.add(bienTheReq.getMauSac());
        params.add(bienTheReq.getKichCo());
        params.add(bienTheReq.getGia());
        params.add(bienTheReq.getTonKho());

        return dbFunctionExecutor.execute(
                "product.fn_sua_bien_the",
                params,
                BienTheRes.class
        );
    }

    public void xoaBienThe(Long id, Long spId) {
        dbFunctionExecutor.execute(
                "product.fn_xoa_bien_the",
                List.of(id, spId),
                Boolean.class
        );
    }

    public SanPhamRes laySanPham(Long id) {
        return dbFunctionExecutor.execute(
                "product.fn_lay_san_pham",
                List.of(id),
                SanPhamRes.class
        );
    }
}
