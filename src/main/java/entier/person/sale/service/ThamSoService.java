package entier.person.sale.service;

import entier.person.sale.dto.req.ParamReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.ThamSoRes;
import entier.person.sale.repository.ThamSoRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ThamSoService {
    private final ThamSoRepo thamSoRepo;

    public PageResponse<ThamSoRes> layDsThamSo(String search, int page, int size) {
        return thamSoRepo.layDsThamSo(search, page, size);
    }

    public String layThamSo(String khoa) {
        return thamSoRepo.layThamSo(khoa);
    }

    public ThamSoRes taoThamSo(ParamReq tinhReq) {
        return thamSoRepo.taoThamSo(tinhReq);
    }

    public ThamSoRes suaThamSo(Long id, ParamReq tinhReq) {
        return thamSoRepo.suaThamSo(id, tinhReq);
    }

    public void xoaThamSo(Long id) {
        thamSoRepo.xoaThamSo(id);
    }
}
