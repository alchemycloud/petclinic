package co.aleksa.backend.service;

import static co.aleksa.backend.Constants.TENANT_SERVER_EXTENSION;

import co.aleksa.backend.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class TenantServiceProd implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceProd.class);

    @Inject private DataSource jdbcTemplate;

    @Override
    public List<String> getTenants() throws SQLException {

        try (Connection conn = jdbcTemplate.getConnection()) {
            final Statement stmt = conn.createStatement();
            final String sql = "SELECT distinct TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA like '%" + TENANT_SERVER_EXTENSION + "'";
            final ResultSet rs = stmt.executeQuery(sql);

            final List<String> tenants = new ArrayList<>();
            while (rs.next()) {
                final String table_schema = rs.getString("TABLE_SCHEMA");
                if (!table_schema.startsWith(Constants.DEFAULT_TEST_TENANT)) {
                    tenants.add(table_schema.replace(TENANT_SERVER_EXTENSION, ""));
                }
            }
            return tenants;
        }
    }

    @Override
    public List<String> getTenantsByEmail(String email) throws SQLException {
        return getTenantsBy("email", email);
    }

    @Override
    public List<String> getTenantsBySetPasswordCode(String code) throws SQLException {
        return getTenantsBy("setPasswordCode", code);
    }

    private List<String> getTenantsBy(String field, String value) throws SQLException {

        int counter = 1;

        try (Connection conn = jdbcTemplate.getConnection()) {

            final List<String> tenants = getTenants();
            final List<String> findUsers =
                    tenants.stream()
                            .map(
                                    tenant ->
                                            "SELECT id, '"
                                                    + tenant
                                                    + "' as tenant FROM `"
                                                    + tenant
                                                    + TENANT_SERVER_EXTENSION
                                                    + "`.`User` WHERE  "
                                                    + field
                                                    + "= ?")
                            .toList();
            final String sql = String.join(" UNION ", findUsers);

            final PreparedStatement stmt = conn.prepareStatement(sql);
            for (String tenant : tenants) {
                stmt.setString(counter++, value);
            }

            LOG.info(stmt.toString());
            final ResultSet rs = stmt.executeQuery();

            final List<String> matchedTenants = new ArrayList<>();
            while (rs.next()) {
                matchedTenants.add(rs.getString("tenant"));
            }
            return matchedTenants;
        }
    }
}
