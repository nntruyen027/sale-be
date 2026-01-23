package entier.person.sale.service;

import entier.person.sale.dto.req.ChuyenMucReq;
import entier.person.sale.dto.res.ChuyenMucRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.repository.ChuyenMucRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChuyenMucService {
    private final ChuyenMucRepo chuyenMucRepo;

    public PageResponse<ChuyenMucRes> layDsChuyenMuc(String search, int page, int size) {
        return chuyenMucRepo.layDsChuyenMuc(search, page, size);
    }

    public ChuyenMucRes taoChuyenMuc(ChuyenMucReq chuyenMucReq) {
        return chuyenMucRepo.taoChuyenMuc(chuyenMucReq);
    }

    public ChuyenMucRes suaChuyenMuc(Long id, ChuyenMucReq chuyenMucReq) {
        return chuyenMucRepo.suaChuyenMuc(id, chuyenMucReq);
    }

    public void xoaChuyenMuc(Long id) {
        chuyenMucRepo.xoaChuyenMuc(id);
    }
}
