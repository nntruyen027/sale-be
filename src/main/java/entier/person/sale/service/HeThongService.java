package entier.person.sale.service;

import entier.person.sale.dto.req.BannerReq;
import entier.person.sale.dto.res.BannerRes;
import entier.person.sale.repository.HeThongRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HeThongService {
    private final HeThongRepo heThongRepo;

    public List<BannerRes> layDsBanner() {
        return heThongRepo.layDsBanner();
    }

    public BannerRes taoBanner(BannerReq bannerReq) {
        return heThongRepo.taoBanner(bannerReq);
    }

    public BannerRes suaBanner(Long id, BannerReq bannerReq) {
        return heThongRepo.suaBanner(id, bannerReq);
    }

    public void xoaBanner(Long id) {
        heThongRepo.xoaBanner(id);
    }
}
