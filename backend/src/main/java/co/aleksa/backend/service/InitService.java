package co.aleksa.backend.service;

import co.aleksa.backend.model.DataProcessorLog;
import co.aleksa.backend.model.DataVersion;
import co.aleksa.backend.repository.DataProcessorLogRepository;
import co.aleksa.backend.service.data.DataProcessor;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import liquibase.exception.LiquibaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InitService {
    private static final Logger LOG = LoggerFactory.getLogger(InitService.class);

    @Inject private DataService dataService;

    @Inject private DataProcessorLogRepository dataProcessorLogRepository;

    @Inject private List<DataProcessor> dataProcessors;

    public abstract void init() throws SQLException, LiquibaseException;

    public void runDataProcessors() {
        LOG.debug("Run data processors");

        final DataVersion currentVersion = dataService.getCurrentVersion().orElseGet(() -> dataService.createFirstVersion());
        LOG.info("Initializing data from version " + currentVersion);

        if (dataService.lockVersion()) {
            try {
                final List<DataProcessorLog> executedLogs = dataProcessorLogRepository.findAll();
                final List<DataProcessor> missingProcessors =
                        dataProcessors.stream()
                                .filter(
                                        processor ->
                                                executedLogs.stream()
                                                        .noneMatch(
                                                                log ->
                                                                        processor.compare(log.getMajor(), log.getMinor(), log.getRevision(), log.getNumber())
                                                                                == 0))
                                .sorted()
                                .toList();
                for (DataProcessor processor : missingProcessors) {
                    processor.process();
                }
            } catch (Exception e) {
                LOG.info("Exception during data initialization.");
                throw e;
            } finally {
                dataService.unlockVersion();
            }
        }
    }
}
