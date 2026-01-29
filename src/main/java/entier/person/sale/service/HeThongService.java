package entier.person.sale.service;

import entier.person.sale.repository.HeThongRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HeThongService {
    private final HeThongRepo heThongRepo;

    public Object SetUpCauHinh(String object, String cauHinh) {
        return heThongRepo.setUpCauHinh(object, cauHinh);
    }

    public Object layCauHinh(String cauHinh) {
        return heThongRepo.layHomeCauHinh(cauHinh);
    }
}
