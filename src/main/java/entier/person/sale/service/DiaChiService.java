package entier.person.sale.service;

import entier.person.sale.dto.req.DiaChiReq;
import entier.person.sale.dto.res.DiaChiRes;
import entier.person.sale.repository.DiaChiRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DiaChiService {
    private final DiaChiRepo diaChiRepo;

    public List<DiaChiRes> layDsDiaChiCaNhan(Long userId) {
        return diaChiRepo.layDsDiaChiCaNhan(userId);
    }

    public DiaChiRes themDiaChiCaNhan(Long id, DiaChiReq diaChiReq) {
        return diaChiRepo.themDiaChiCaNhan(id, diaChiReq);
    }

    public DiaChiRes suaDiaChiCaNhan(Long id, Long userId, DiaChiReq diaChiReq) {
        return diaChiRepo.suaDiaChiCaNhan(id, userId, diaChiReq);
    }

    public void xoaDiaChiCaNhan(Long id, Long userId) {
        diaChiRepo.xoaDiaChiCaNhan(id, userId);
    }
}
