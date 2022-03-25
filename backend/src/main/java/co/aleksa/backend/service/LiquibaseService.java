package co.aleksa.backend.service;

import co.aleksa.backend.util.AppThreadLocals;
import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Inject;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LiquibaseService {
    private static final Logger LOG = LoggerFactory.getLogger(LiquibaseService.class);

    @Inject private MultiTenantConnectionProvider multiTenantConnectionProvider;

    public boolean initLiquibase() throws LiquibaseException, SQLException {
        LOG.debug("Initializing liquibase");
        final String tenant = AppThreadLocals.getTenant().get();

        try (final Connection connection = multiTenantConnectionProvider.getConnection(tenant)) {
            LOG.debug("Liquibase connection acquired for {}", tenant + "_backend");
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(tenant + "_backend");
            final Liquibase liquibase = new Liquibase("liquibase/db-changelog.xml", new ClassLoaderResourceAccessor(), database);
            if (liquibase.listLocks().length > 0) {
                LOG.debug("Liquibase already locked.");
                return false;
            }
            LOG.debug("Liquibase update started");
            liquibase.update(new Contexts(), new LabelExpression());
            return true;
        }
    }
}
