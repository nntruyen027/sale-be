package entier.person.sale.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
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
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);

            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return null;

            // 👉 primitive / wrapper
            if (Number.class.isAssignableFrom(clazz)
                    || clazz == String.class
                    || clazz == Boolean.class) {

                return rs.getObject(1, clazz);
            }

            String json = rs.getString(1);
            if (json == null)
                return null;

            return objectMapper.readValue(json, clazz);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error executing function: " + functionName, e);
        }
    }

    public <T> T execute(
            String functionName,
            List<Object> params,
            TypeReference<T> typeReference) {

        String sql = buildSql(functionName, params.size());

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);

            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return null;

            String json = rs.getString(1);
            if (json == null)
                return null;

            return objectMapper.readValue(json, typeReference);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error executing function: " + functionName, e);
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
                java.util.Collections.nCopies(count, "?"));
    }

    private void setParams(PreparedStatement ps, List<Object> params)
            throws SQLException {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }
}
