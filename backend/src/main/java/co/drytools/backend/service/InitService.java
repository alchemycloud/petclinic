package co.drytools.backend.service;

import co.drytools.backend.model.DataProcessorLog;
import co.drytools.backend.model.DataVersion;
import co.drytools.backend.repository.DataProcessorLogRepository;
import co.drytools.backend.service.data.DataProcessor;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    private static final Logger LOG = LoggerFactory.getLogger(InitService.class);

    @Inject private DataService dataService;

    @Inject private DataProcessorLogRepository dataProcessorLogRepository;

    @Inject private List<DataProcessor> dataProcessors;

    @Inject private SpringLiquibase liquibase;

    @PostConstruct
    public void init() {
        runDataProcessors();
    }

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
                                .collect(Collectors.toList());
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
