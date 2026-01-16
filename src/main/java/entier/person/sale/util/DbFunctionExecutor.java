package entier.person.sale.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class DbFunctionExecutor {

    private final DataSource dataSource;
    private final ObjectMapper objectMapper;

    public <T> T execute(
            String functionName,
            List<Object> params,
            Class<T> clazz) {

        String sql = buildSql(functionName, params.size());

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            setParams(ps, params);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            // primitive / wrapper
            if (Number.class.isAssignableFrom(clazz)
                    || clazz == String.class
                    || clazz == Boolean.class) {

                return rs.getObject(1, clazz);
            }

            String json = rs.getString(1);
            if (json == null) {
                return null;
            }

            return objectMapper.readValue(json, clazz);

        } catch (SQLException e) {

            String msg = e.getMessage();

            if (msg != null) {
                // Bỏ phần "ERROR: "
                if (msg.startsWith("ERROR:")) {
                    msg = msg.substring(6).trim();
                }

                // Bỏ phần Where / line / function
                int newLineIndex = msg.indexOf("\n");
                if (newLineIndex > 0) {
                    msg = msg.substring(0, newLineIndex).trim();
                }
            }

            throw new RuntimeException(msg, e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> T execute(
            String functionName,
            List<Object> params,
            TypeReference<T> typeReference) {

        String sql = buildSql(functionName, params.size());

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            setParams(ps, params);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            String json = rs.getString(1);
            if (json == null) {
                return null;
            }

            return objectMapper.readValue(json, typeReference);

        } catch (SQLException e) {

            String msg = e.getMessage();

            if (msg != null) {
                // Bỏ phần "ERROR: "
                if (msg.startsWith("ERROR:")) {
                    msg = msg.substring(6).trim();
                }

                // Bỏ phần Where / line / function
                int newLineIndex = msg.indexOf("\n");
                if (newLineIndex > 0) {
                    msg = msg.substring(0, newLineIndex).trim();
                }
            }

            throw new RuntimeException(msg, e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String buildSql(String functionName, int paramCount) {
        String placeholders = paramCount == 0
                ? ""
                : paramsPlaceholder(paramCount);

        return "SELECT " + functionName + "(" + placeholders + ")";
    }

    private String paramsPlaceholder(int count) {
        return String.join(
                ", ",
                java.util.Collections.nCopies(count, "?")
        );
    }

    private void setParams(PreparedStatement ps, List<Object> params)
            throws SQLException {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }
}
