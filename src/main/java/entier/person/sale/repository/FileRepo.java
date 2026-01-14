package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.FileReq;
import entier.person.sale.dto.res.FileRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FileRepo {

    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<FileRes> layTatCaFile(String search, int page, int limit) {

        return dbFunctionExecutor.execute(
                "fn_lay_tat_ca_file",
                List.of(search, page, limit),
                new TypeReference<PageResponse<FileRes>>() {
                }
        );
    }

    public FileRes luuFile(FileReq fileReq) {
        return dbFunctionExecutor.execute(
                "fn_tao_file",
                List.of(
                        fileReq.getFileName(),
                        fileReq.getStoredName(),
                        fileReq.getUrl(),
                        fileReq.getContentType(),
                        fileReq.getSize(),
                        fileReq.getUserId()
                ),
                FileRes.class
        );
    }

    public Boolean xoaFile(Long id) {
        return dbFunctionExecutor.execute(
                "fn_xoa_file",
                List.of(id),
                Boolean.class
        );
    }
}
