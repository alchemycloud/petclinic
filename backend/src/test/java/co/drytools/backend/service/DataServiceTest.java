package co.drytools.backend.service;

import co.drytools.backend.AbstractDatabaseTest;
import co.drytools.backend.model.DataProcessorLog;
import co.drytools.backend.model.DataVersion;
import co.drytools.backend.repository.DataProcessorLogRepository;
import co.drytools.backend.repository.DataVersionRepository;
import co.drytools.backend.service.data.DataProcessor;
import co.drytools.backend.util.TimeUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("ApiTestCategory0")
public class DataServiceTest extends AbstractDatabaseTest {
    @Inject private DataVersionRepository dataVersionRepository;

    @Inject private DataProcessorLogRepository dataProcessorLogRepository;
    @Inject private List<DataProcessor> dataProcessors;

    @Inject private InitService initService;

    @Test
    public void testInitData() {
        // setup

        // method
        takeSnapshot();
        initService.runDataProcessors();

        final DataVersion dataVersion = dataVersionRepository.findAll().get(0);
        assertObject(
                dataVersion,
                notNull(DataVersion.ID),
                value(DataVersion.LOCK, false),
                value(DataVersion.MAJOR, 1),
                value(DataVersion.MINOR, 0),
                value(DataVersion.REVISION, 0),
                value(DataVersion.NUMBER, 1),
                value(DataVersion.TIME, dataVersion.getTime()),
                value(DataVersion.LOCK_TIME, dataVersion.getLockTime()));

        final List<DataProcessorLog> logs = dataProcessorLogRepository.findAll();

        final List<DataProcessor> sortedDataProcessors = dataProcessors.stream().sorted().collect(Collectors.toList());

        for (int i = 0; i < sortedDataProcessors.size(); i++) {
            final DataProcessor dataProcessor = sortedDataProcessors.get(i);
            assertObject(
                    logs.get(i),
                    ignored(DataProcessorLog.ID),
                    value(DataProcessorLog.MAJOR, dataProcessor.major()),
                    value(DataProcessorLog.MINOR, dataProcessor.minor()),
                    value(DataProcessorLog.REVISION, dataProcessor.revision()),
                    value(DataProcessorLog.NUMBER, dataProcessor.number()),
                    value(DataProcessorLog.STARTED, TimeUtil.now()),
                    value(DataProcessorLog.DESCRIPTION, dataProcessor.description().orElse("")),
                    value(DataProcessorLog.COMPLETED, Optional.of(TimeUtil.now())));
        }
    }
}
