package co.aleksa.backend.config;

import co.aleksa.backend.interceptor.QueryCountInterceptor;
import com.querydsl.codegen.ClassPathUtils;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(value = "co.aleksa.backend.repository", bootstrapMode = BootstrapMode.LAZY)
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl)
            throws IOException {

        final Map<String, Object> properties = new HashMap<>();
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
        properties.put("hibernate.session_factory.interceptor", new QueryCountInterceptor());
        properties.put("javax.persistence.query.timeout", "30000");

        // necessary to because of the deadlock in hibernate
        ClassPathUtils.scanPackage(Thread.currentThread().getContextClassLoader(), "co.aleksa.backend.model");

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("co.aleksa.backend.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public JPQLQueryFactory factory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
