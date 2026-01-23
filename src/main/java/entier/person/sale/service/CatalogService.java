package entier.person.sale.service;

import entier.person.sale.dto.req.CatalogReq;
import entier.person.sale.dto.res.CatalogRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.repository.CatalogRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatalogService {
    private final CatalogRepo catalogRepo;

    public PageResponse<CatalogRes> layDsCatalog(String search, int page, int size) {
        return catalogRepo.layDsCatalog(search, page, size);
    }

    public CatalogRes taoCatalog(CatalogReq catalogReq) {
        return catalogRepo.taoCatalog(catalogReq);
    }

    public CatalogRes suaCatalog(Long id, CatalogReq catalogReq) {
        return catalogRepo.suaCatalog(id, catalogReq);
    }

    public void xoaCatalog(Long id) {
        catalogRepo.xoaCatalog(id);
    }
}
