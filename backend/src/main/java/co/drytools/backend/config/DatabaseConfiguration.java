package co.drytools.backend.config;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(value = "co.drytools.backend.repository", bootstrapMode = BootstrapMode.LAZY)
@EnableTransactionManagement
public class DatabaseConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private static final int MAX_LIFETIME = 1800000; // 30 minutes

    private static final int LEAK_THRESHOLD = 120000; // 120 sec, this needs to take into account application startup time

    private static final int CONNECTION_TIMEOUT = 500000;

    private static final int MAX_POOL = 20;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(DataSourceProperties dataSourceProperties, CustomProperties customProperties) {

        LOG.debug("Initializing datasource...");

        final HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
        config.addDataSourceProperty("url", dataSourceProperties.getUrl());
        config.addDataSourceProperty("user", dataSourceProperties.getUsername());
        config.addDataSourceProperty("password", dataSourceProperties.getPassword() == null ? "" : dataSourceProperties.getPassword());

        // mysql specific
        config.addDataSourceProperty("cachePrepStmts", customProperties.getDatasource().isCachePrepStmts());
        config.addDataSourceProperty("prepStmtCacheSize", customProperties.getDatasource().getPrepStmtCacheSize());
        config.addDataSourceProperty("prepStmtCacheSqlLimit", customProperties.getDatasource().getPrepStmtCacheSqlLimit());
        config.addDataSourceProperty("useServerPrepStmts", customProperties.getDatasource().isUseServerPrepStmts());

        config.setMaxLifetime(MAX_LIFETIME);
        config.setLeakDetectionThreshold(LEAK_THRESHOLD);
        // https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
        // should be 2*number of cores + number of spindles (disk heads)
        // connection timeout should be configured to lower value than default(30 seconds), since health check is waiting too long to get connection from pool
        config.setConnectionTimeout(CONNECTION_TIMEOUT);
        config.setMaximumPoolSize(MAX_POOL);
        config.setMinimumIdle(MAX_POOL / 4);
        config.setValidationTimeout(CONNECTION_TIMEOUT / 2);

        LOG.debug("Connection max lifetime (ms): " + config.getMaxLifetime());
        LOG.debug("Connection timeout (ms): " + config.getConnectionTimeout());
        LOG.debug("Connection pool minimum idle size: " + config.getMinimumIdle());
        LOG.debug("Connection pool max size: " + config.getMaximumPoolSize());
        LOG.debug("Validation timeout (ms): " + config.getValidationTimeout()); // default 5000, double it
        LOG.debug("Leak detection threshold (ms): " + config.getLeakDetectionThreshold()); // default 0, set it to 10 secs

        LOG.debug("Datasource initialized.");
        return new HikariDataSource(config);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties, LiquibaseProperties liquibaseProperties) {

        LOG.debug("Initializing liquibase...");

        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/db-changelog.xml");
        return liquibase;
    }

    @Bean
    public JPQLQueryFactory factory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
