package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.XaReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.XaRes;
import entier.person.sale.util.DbFunctionExecutor;
import entier.person.sale.util.ExcelBatchImporter;
import entier.person.sale.util.ExcelUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
@AllArgsConstructor
public class XaRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final DbFunctionExecutor dbFunctionExecutor;


    public PageResponse<XaRes> layTatCaXa(String search, Long tinhId, int page, int size) {
        List<Object> params = new ArrayList<>();
        params.add(search);
        params.add(tinhId);
        params.add(page);
        params.add(size);

        PageResponse<XaRes> data = dbFunctionExecutor.execute(
                "dm_chung.fn_lay_tat_ca_xa",
                params,
                new TypeReference<PageResponse<XaRes>>() {
                });


        return data;

    }

    @Transactional
    public XaRes taoXa(XaReq xa) {
        return dbFunctionExecutor.execute(
                "dm_chung.fn_tao_xa",
                List.of(xa.getTen(), xa.getGhiChu(), xa.getTinhId()),
                XaRes.class);
    }

    @Transactional
    public XaRes suaXa(Long id, XaReq xa) {
        return dbFunctionExecutor.execute(
                "dm_chung.fn_sua_xa",
                List.of(id, xa.getTen(), xa.getGhiChu(), xa.getTinhId()),
                XaRes.class);

    }

    @Transactional
    public boolean xoaXa(Long id) {
        return dbFunctionExecutor.execute(
                "dm_chung.fn_xoa_xa",
                List.of(id),
                Boolean.class);

    }

    @Transactional
    public void importXa(MultipartFile file) throws IOException {
        ExcelBatchImporter batchImporter = new ExcelBatchImporter(entityManager);
        int batchSize = 100;

        batchImporter.importExcelBatch(
                file,
                batchSize,
                row -> {
                    String ten = ExcelUtils.getCellValue(row.getCell(1));
                    String ghiChu = ExcelUtils.getCellValue(row.getCell(3));
                    String tinhId = ExcelUtils.getCellValue(row.getCell(2)).split(" - ")[0];

                    if (ten == null || ten.isEmpty()) {
                        throw new RuntimeException("Tên xã bị trống ở dòng " + (row.getRowNum() + 1));
                    }
                    if (tinhId == null || tinhId.isEmpty()) {
                        throw new RuntimeException("Tên tỉnh bị trống ở dòng " + (row.getRowNum() + 1));
                    }

                    return new Object[]{ten, ghiChu, tinhId};
                },
                "dm_chung.fn_import_xa", "dm_chung.xa_input"
        );
    }
}
