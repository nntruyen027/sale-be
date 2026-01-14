package entier.person.sale.util;

import jakarta.persistence.EntityManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ExcelBatchImporter {

    private final EntityManager entityManager;

    public ExcelBatchImporter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Generic batch import from Excel, reusable cho mọi composite type
     *
     * @param file       MultipartFile Excel
     * @param batchSize  số row gửi 1 lần đến DB
     * @param mapper     map Row -> Object[] (1 row dữ liệu)
     * @param sql        tên function batch trong DB
     * @param pgTypeName tên composite type PostgreSQL, ví dụ: "truong_input"
     */
    public void importExcelBatch(MultipartFile file, int batchSize,
                                 Function<Row, Object[]> mapper, String sql, String pgTypeName) throws IOException {

        List<Object[]> batch = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header
                Object[] data = mapper.apply(row);
                batch.add(data);

                if (batch.size() >= batchSize) {
                    callFunctionBatch(sql, batch, pgTypeName);
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                callFunctionBatch(sql, batch, pgTypeName);
            }
        }
    }

    private void callFunctionBatch(String sql, List<Object[]> batch, String pgTypeName) {
        if (batch.isEmpty()) return;

        List<String> rowLiterals = new ArrayList<>();
        for (Object[] row : batch) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < row.length; i++) {
                Object val = row[i];
                if (val == null) {
                    sb.append("NULL");
                } else if (val instanceof Number) {      // BIGINT, INT
                    sb.append(val);
                } else {
                    String str = val.toString().replace("'", "''");
                    sb.append("'").append(str).append("'");
                }
                if (i < row.length - 1) sb.append(",");
            }
            sb.append(")");
            rowLiterals.add(sb.toString());
        }

        String arrayLiteral = "ARRAY[" + String.join(",", rowLiterals) + "]::" + pgTypeName + "[]";

        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            String sqlToExecute = "SELECT " + sql + "(" + arrayLiteral + ")";
            try (var ps = connection.prepareStatement(sqlToExecute)) {
                ps.execute();
            }
        });
    }
}
