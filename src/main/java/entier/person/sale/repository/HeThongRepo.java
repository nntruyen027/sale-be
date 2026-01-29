package entier.person.sale.repository;

import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class HeThongRepo {
    private final DbFunctionExecutor dbFunctionExecutor;

    public Object setUpCauHinh(String object, String cauHinh) {
        return dbFunctionExecutor.execute(
                "config.fn_setup_" + cauHinh,
                List.of(object),
                Object.class
        );
    }

    public Object layHomeCauHinh(String cauHinh) {
        return dbFunctionExecutor.execute(
                "config.fn_lay_" + cauHinh,
                List.of(),
                Object.class
        );
    }
}
