package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.TinhReq;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.dto.res.TinhRes;
import entier.person.sale.util.DbFunctionExecutor;
import entier.person.sale.util.ExcelBatchImporter;
import entier.person.sale.util.ExcelUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class TinhRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final DbFunctionExecutor dbFunctionExecutor;

    public PageResponse<TinhRes> layTatCaTinh(String search, int page, int size) {

        return dbFunctionExecutor.execute(
                "dm_chung.fn_lay_tat_ca_tinh",
                List.of(search, page, size),
                new TypeReference<PageResponse<TinhRes>>() {
                });

    }

    @Transactional
    public TinhRes taoTinh(TinhReq tinh) {
        return dbFunctionExecutor.execute(
                "dm_chung.fn_tao_tinh",
                List.of(tinh.getTen(), tinh.getGhiChu()), TinhRes.class);

    }

    @Transactional
    public TinhRes suaTinh(Long id, TinhReq tinh) {
        return dbFunctionExecutor.execute(
                "dm_chung.fn_sua_tinh",
                List.of(id, tinh.getTen(), tinh.getGhiChu()), TinhRes.class);

    }

    @Transactional
    public boolean xoaTinh(Long id) {
        return dbFunctionExecutor.execute(
                "dm_chung.fn_xoa_tinh",
                List.of(id), Boolean.class);
    }

    @Transactional
    public void importTinh(MultipartFile file) throws IOException {
        ExcelBatchImporter batchImporter = new ExcelBatchImporter(entityManager);
        int batchSize = 100;

        batchImporter.importExcelBatch(
                file,
                batchSize,
                row -> {
                    String ten = ExcelUtils.getCellValue(row.getCell(1));
                    String ghiChu = ExcelUtils.getCellValue(row.getCell(2));

                    if (ten == null || ten.isEmpty()) {
                        throw new RuntimeException("Tên tỉnh bị trống ở dòng " + (row.getRowNum() + 1));
                    }


                    return new Object[]{ten, ghiChu};
                },
                "dm_chung.fn_import_tinh", "dm_chung.tinh_input"
        );
    }
}
