package entier.person.sale.service;

import entier.person.sale.dto.req.RoleReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.RoleRes;
import entier.person.sale.repository.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VaiTroService {
    private final RoleRepo roleRepo;

    public PageResponse<RoleRes> layTatCaVaiTro(String search, int page, int size) {
        return roleRepo.layTatCaVaiTro(search, page, size);

    }

    public RoleRes phanQuyenChoVaiTro(Long id, List<String> permissions) {
        return (roleRepo.phanQuyenChoVaiTro(id, permissions));
    }

    public RoleRes taoVaiTro(RoleReq roleReq) {
        return (roleRepo.taoVaiTro(roleReq));
    }

    public RoleRes suaVaiTro(Long id, RoleReq roleReq) {
        return (roleRepo.suaVaiTro(id, roleReq));
    }

    public void xoaVaiTro(Long id) {
        roleRepo.xoaVaiTro(id);
    }
}
