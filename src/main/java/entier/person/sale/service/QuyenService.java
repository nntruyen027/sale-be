package entier.person.sale.service;

import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.PermissionRes;
import entier.person.sale.repository.PermissionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuyenService {
    private final PermissionRepo permissionRepo;

    public PageResponse<PermissionRes> layTatCaQuyen(String search, int page, int size) {

        return permissionRepo.layTatCaQuyen(search, page, size);
    }
}
