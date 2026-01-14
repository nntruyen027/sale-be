package entier.person.sale.util;

@FunctionalInterface
public interface ExcelRowMapper<T> {
    T mapRow(org.apache.poi.ss.usermodel.Row row) throws Exception;
}

