package entier.person.sale.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class XaRes {
    private Long id;

    private String ten;

    private String ghiChu;

    private TinhRes tinh;
}
