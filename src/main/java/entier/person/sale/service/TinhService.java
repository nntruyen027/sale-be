package entier.person.sale.service;

import entier.person.sale.dto.req.TinhReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.TinhRes;
import entier.person.sale.repository.TinhRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class TinhService {
    private final TinhRepo tinhRepo;

    public PageResponse<TinhRes> layDsTinh(String search, int page, int size) {
        return tinhRepo
                .layTatCaTinh(search, page, size);
    }

    public TinhRes taoTinh(TinhReq tinhReq) {
        return (tinhRepo.taoTinh(tinhReq));
    }

    public TinhRes suaTinh(Long id, TinhReq tinhReq) {
        return (tinhRepo.suaTinh(id, tinhReq));
    }

    public void xoaTinh(Long id) {
        tinhRepo.xoaTinh(id);
    }

    public void importTinh(MultipartFile file) throws IOException {
        tinhRepo.importTinh(file);
    }
}
