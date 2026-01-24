package entier.person.sale.dto.res;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CatalogRes {
    Long id;
    String tieuDe;
    String anhBia;
    String url;
    Timestamp ngayTao;
}
