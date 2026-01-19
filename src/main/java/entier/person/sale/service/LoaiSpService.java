package entier.person.sale.service;

import entier.person.sale.dto.req.LoaiSpReq;
import entier.person.sale.dto.res.LoaiSpRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.repository.LoaiSpRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoaiSpService {
    private final LoaiSpRepo loaiSpRepo;

    public PageResponse<LoaiSpRes> layDsLoaiSp(String search, int page, int size) {
        return loaiSpRepo.layDsLoaiSp(search, page, size);
    }

    public LoaiSpRes taoLoaiSp(LoaiSpReq loaiSpReq) {
        return loaiSpRepo.taoLoaiSp(loaiSpReq);
    }

    public LoaiSpRes suaLoaiSp(Long id, LoaiSpReq loaiSpReq) {
        return loaiSpRepo.suaLoaiSp(id, loaiSpReq);
    }

    public void xoaLoaiSp(Long id) {
        loaiSpRepo.xoaLoaiSp(id);
    }
}
