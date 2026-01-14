package entier.person.sale.service;

import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.PermissionRes;
import entier.person.sale.repository.PermissionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuyenService {
    private final PermissionRepo permissionRepo;

    public PageResponse<PermissionRes> layTatCaQuyen(String search, int page, int size) {

        List<PermissionRes> data = permissionRepo.layTatCaQuyen(search, page, size)
                .stream().toList();

        long totalElements = permissionRepo.demTatCaQuyen(search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<PermissionRes>builder()
                .data(data)
                .size(size)
                .page(page)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }
}
