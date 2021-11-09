package co.drytools.backend.repository;

import co.drytools.backend.model.DataProcessorLog;
import co.drytools.backend.model.id.DataProcessorLogId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface DataProcessorLogRepository extends SimpleRepository<DataProcessorLog, DataProcessorLogId> {

    DataProcessorLog create(
            Integer major, Integer minor, Integer revision, Integer number, String description, ZonedDateTime started, Optional<ZonedDateTime> completed);

    List<DataProcessorLog> findByMajor(Integer major);

    List<DataProcessorLog> findByMinor(Integer minor);

    List<DataProcessorLog> findByRevision(Integer revision);

    List<DataProcessorLog> findByNumber(Integer number);

    List<DataProcessorLog> findByDescription(String description);

    List<DataProcessorLog> findByStarted(ZonedDateTime started);

    List<DataProcessorLog> findByCompleted(Optional<ZonedDateTime> completed);

    List<DataProcessorLog> findByCompletedMandatory(ZonedDateTime completed);

    Optional<DataProcessorLog> findByMajorAndMinorAndRevisionAndNumber(Integer major, Integer minor, Integer revision, Integer number);
}
