package entier.person.sale.service;

import entier.person.sale.dto.req.BienTheReq;
import entier.person.sale.dto.req.SanPhamReq;
import entier.person.sale.dto.res.BienTheRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.SanPhamRes;
import entier.person.sale.repository.SanPhamRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SanPhamService {
    private final SanPhamRepo sanPhamRepo;

    public PageResponse<SanPhamRes> layDsSanPham(Long loaiId, String search, int page, int size) {
        return sanPhamRepo.layDsSanPham(loaiId, search, page, size);
    }

    public SanPhamRes taoSanPham(SanPhamReq sanPhamReq) {
        return sanPhamRepo.taoSanPham(sanPhamReq);
    }

    public SanPhamRes suaSanPham(Long id, SanPhamReq sanPhamReq) {
        return sanPhamRepo.suaSanPham(id, sanPhamReq);
    }

    public void xoaSanPham(Long id) {
        sanPhamRepo.xoaSanPham(id);
    }

    public BienTheRes themBienThe(Long spId, BienTheReq bienTheReq) {
        return sanPhamRepo.themBienThe(spId, bienTheReq);
    }

    public BienTheRes suaBienthe(Long id, Long spId, BienTheReq bienTheReq) {
        return sanPhamRepo.suaBienThe(id, spId, bienTheReq);
    }

    public void xoaBienThe(Long id, Long spId) {
        sanPhamRepo.xoaBienThe(id, spId);
    }

    public SanPhamRes laySanPham(Long id) {
        return sanPhamRepo.laySanPham(id);
    }
}
