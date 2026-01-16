package entier.person.sale.service;

import entier.person.sale.dto.req.XaReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.XaRes;
import entier.person.sale.repository.XaRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class XaService {
    private final XaRepo xaRepo;

    public PageResponse<XaRes> layDsXa(String search, Long tinhId, int page, int size) {
        return xaRepo
                .layTatCaXa(search, tinhId, page, size);
    }

    public XaRes taoXa(XaReq xaReq) {
        return (xaRepo.taoXa(xaReq));
    }

    public XaRes suaXa(Long id, XaReq xaReq) {
        return (xaRepo.suaXa(id, xaReq));
    }

    public void xoaXa(Long id) {
        xaRepo.xoaXa(id);
    }

    public void importXa(MultipartFile file) throws IOException {
        xaRepo.importXa(file);
    }
}
