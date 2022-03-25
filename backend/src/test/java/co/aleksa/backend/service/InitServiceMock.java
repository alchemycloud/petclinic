package co.aleksa.backend.service;

import co.aleksa.backend.Constants;
import co.aleksa.backend.util.AppThreadLocals;
import java.sql.SQLException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import liquibase.exception.LiquibaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class InitServiceMock extends InitService {
    private static final Logger LOG = LoggerFactory.getLogger(InitServiceMock.class);

    @Inject private LiquibaseService liquibaseService;

    @Override
    @PostConstruct
    public void init() throws SQLException, LiquibaseException {
        AppThreadLocals.initialize(Optional.empty(), Optional.of(Constants.DEFAULT_TEST_TENANT), Optional.empty());
        liquibaseService.initLiquibase();
    }
}
