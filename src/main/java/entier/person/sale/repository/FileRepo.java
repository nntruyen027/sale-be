package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.FileReq;
import entier.person.sale.dto.res.FileRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class FileRepo {

    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<FileRes> layTatCaFile(String search, int page, int limit) {
        List<Object> params = new ArrayList<>();
        params.add(search);
        params.add(page);
        params.add(limit);
        return dbFunctionExecutor.execute(
                "fn_lay_tat_ca_file",
                params,
                new TypeReference<PageResponse<FileRes>>() {
                }
        );
    }

    public FileRes luuFile(FileReq fileReq) {
        List<Object> params = new ArrayList<>();
        params.add(fileReq.getFileName());
        params.add(fileReq.getStoredName());
        params.add(fileReq.getUrl());
        params.add(fileReq.getContentType());
        params.add(fileReq.getSize());
        params.add(fileReq.getUserId());
        return dbFunctionExecutor.execute(
                "fn_tao_file",
                params,
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
