package entier.person.sale.service;

import entier.person.sale.dto.req.RegiserReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NguoiDungService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public PageResponse<UserFullRes> layTatCaNguoiDung(String search, int page, int limit) {
        return userRepo.layDsNguoiDung(search, page, limit);
    }

    public UserFullRes taoNguoiDung(RegiserReq nguoiDungReq) {
        nguoiDungReq.setPassword(passwordEncoder.encode(nguoiDungReq.getPassword()));
        return userRepo.dangKy(nguoiDungReq);
    }

    public UserFullRes suaNguoiDung(Long id, RegiserReq nguoiDungReq) {
        nguoiDungReq.setPassword(passwordEncoder.encode(nguoiDungReq.getPassword()));
        return userRepo.suaNguoiDung(id, nguoiDungReq);
    }

    public UserFullRes phanVaiTroChoNguoiDung(Long id, List<String> dsMaQuyen) {
        return userRepo.phanVaiTroChoNguoiDung(id, dsMaQuyen);
    }

    public void xoaNguoiDung(Long id) {
        userRepo.xoaNguoiDung(id);
    }
}
