package co.aleksa.backend.tenant;

import co.aleksa.backend.util.AppThreadLocals;
import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
    private static final Logger LOG = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);

    @Inject private DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();

        String tenant = tenantIdentifier;
        if (StringUtils.isBlank(tenantIdentifier)) {
            tenant = AppThreadLocals.getTenant().get();
        }

        final String tenantSchema = tenant + "_backend";
        try {
            final String query = "USE " + tenantSchema;

            LOG.trace("Using tenant schema: " + tenantSchema);
            LOG.trace("Executing query: " + query);

            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new HibernateException("Could not alter JDBC connection to specified schema [" + tenantSchema + "]", e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}
