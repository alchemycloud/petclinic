package co.drytools.backend.repository.impl;

import co.drytools.backend.model.DataProcessorLog;
import co.drytools.backend.model.QDataProcessorLog;
import co.drytools.backend.model.id.DataProcessorLogId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.DataProcessorLogRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DataProcessorLogRepositoryImpl extends AbstractSimpleRepositoryImpl<DataProcessorLog, DataProcessorLogId> implements DataProcessorLogRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DataProcessorLogRepositoryImpl.class);

    @Override
    protected Class<DataProcessorLog> getDomainClass() {
        return DataProcessorLog.class;
    }

    @Override
    protected EntityPath<DataProcessorLog> getEntityPathBase() {
        return QDataProcessorLog.dataProcessorLog;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QDataProcessorLog.dataProcessorLog.id;
    }

    @Override
    protected DataProcessorLogId getId(DataProcessorLog entity) {
        return entity.getId();
    }

    @Override
    public DataProcessorLog create(
            Integer major, Integer minor, Integer revision, Integer number, String description, ZonedDateTime started, Optional<ZonedDateTime> completed) {
        final DataProcessorLog dataProcessorLog = new DataProcessorLog();
        dataProcessorLog.setMajor(major);
        dataProcessorLog.setMinor(minor);
        dataProcessorLog.setRevision(revision);
        dataProcessorLog.setNumber(number);
        dataProcessorLog.setDescription(description);
        dataProcessorLog.setStarted(started);
        dataProcessorLog.setCompleted(completed);
        entityManager.persist(dataProcessorLog);
        postSave(dataProcessorLog);
        return dataProcessorLog;
    }

    @Override
    public List<DataProcessorLog> findByMajor(Integer major) {
        LOG.trace(".findByMajor()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.major.eq(major))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByMinor(Integer minor) {
        LOG.trace(".findByMinor()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.minor.eq(minor))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByRevision(Integer revision) {
        LOG.trace(".findByRevision()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.revision.eq(revision))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByNumber(Integer number) {
        LOG.trace(".findByNumber()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.number.eq(number))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByDescription(String description) {
        LOG.trace(".findByDescription()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.description.eq(description))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByStarted(ZonedDateTime started) {
        LOG.trace(".findByStarted()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.started.eq(started))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByCompleted(Optional<ZonedDateTime> completed) {
        LOG.trace(".findByCompleted()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(completed.map(qDataProcessorLog.completed::eq).orElse(null))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<DataProcessorLog> findByCompletedMandatory(ZonedDateTime completed) {
        LOG.trace(".findByCompletedMandatory()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return factory.select(qDataProcessorLog)
                .from(qDataProcessorLog)
                .where(qDataProcessorLog.completed.eq(completed))
                .orderBy(qDataProcessorLog.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public Optional<DataProcessorLog> findByMajorAndMinorAndRevisionAndNumber(Integer major, Integer minor, Integer revision, Integer number) {
        LOG.trace(".findByMajorAndMinorAndRevisionAndNumber()");
        final QDataProcessorLog qDataProcessorLog = QDataProcessorLog.dataProcessorLog;
        return Optional.ofNullable(
                factory.select(qDataProcessorLog)
                        .from(qDataProcessorLog)
                        .where(
                                new BooleanBuilder()
                                        .and(qDataProcessorLog.major.eq(major))
                                        .and(qDataProcessorLog.minor.eq(minor))
                                        .and(qDataProcessorLog.revision.eq(revision))
                                        .and(qDataProcessorLog.number.eq(number)))
                        .orderBy(qDataProcessorLog.id.asc().nullsLast())
                        .fetchOne());
    }

    @Override
    public List<DataProcessorLog> findAllById(Collection<DataProcessorLogId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QDataProcessorLog.dataProcessorLog)
                .where(QDataProcessorLog.dataProcessorLog.id.in(ids.stream().map(DataProcessorLogId::getValue).toList()))
                .fetch();
    }
}
