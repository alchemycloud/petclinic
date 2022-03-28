package co.aleksa.backend.service;

import co.aleksa.backend.util.AppThreadLocals;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import liquibase.exception.LiquibaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Profile(value = {"!test"})
public class InitServiceImpl extends InitService {
    private static final Logger LOG = LoggerFactory.getLogger(InitServiceImpl.class);

    @Inject private LiquibaseService liquibaseService;

    @Inject private TenantService tenantService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws SQLException {
        LOG.info("Init service triggered");
        final List<String> tenants = tenantService.getTenants();
        for (String tenant : tenants) {
            AppThreadLocals.initialize(Optional.empty(), Optional.of(tenant), Optional.empty());
            final boolean initLiquibase = initLiquibase();
            if (initLiquibase) {
                initDataProcessorsAndFixes();
            }
        }
    }

    private boolean initLiquibase() {
        try {
            return liquibaseService.initLiquibase();
        } catch (LiquibaseException | SQLException e) {
            LOG.warn("Unable to update liquibase", e);
            return false;
        }
    }

    private void initDataProcessorsAndFixes() {
        runDataProcessors();
    }
}
