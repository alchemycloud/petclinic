package co.drytools.backend.repository.impl;

import co.drytools.backend.model.DataVersion;
import co.drytools.backend.model.QDataVersion;
import co.drytools.backend.model.id.DataVersionId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.DataVersionRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DataVersionRepositoryImpl extends AbstractSimpleRepositoryImpl<DataVersion, DataVersionId> implements DataVersionRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DataVersionRepositoryImpl.class);

    @Override
    protected Class<DataVersion> getDomainClass() {
        return DataVersion.class;
    }

    @Override
    protected EntityPath<DataVersion> getEntityPathBase() {
        return QDataVersion.dataVersion;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QDataVersion.dataVersion.id;
    }

    @Override
    protected DataVersionId getId(DataVersion entity) {
        return entity.getId();
    }

    @Override
    public DataVersion create(Integer major, Integer minor, Integer revision, Integer number, ZonedDateTime time, Boolean lock, ZonedDateTime lockTime) {
        final DataVersion dataVersion = new DataVersion();
        dataVersion.setMajor(major);
        dataVersion.setMinor(minor);
        dataVersion.setRevision(revision);
        dataVersion.setNumber(number);
        dataVersion.setTime(time);
        dataVersion.setLock(lock);
        dataVersion.setLockTime(lockTime);
        entityManager.persist(dataVersion);
        postSave(dataVersion);
        return dataVersion;
    }

    @Override
    public List<DataVersion> findByMajor(Integer major) {
        LOG.trace(".findByMajor()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.major.eq(major)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<DataVersion> findByMinor(Integer minor) {
        LOG.trace(".findByMinor()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.minor.eq(minor)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<DataVersion> findByRevision(Integer revision) {
        LOG.trace(".findByRevision()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.revision.eq(revision)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<DataVersion> findByNumber(Integer number) {
        LOG.trace(".findByNumber()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.number.eq(number)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<DataVersion> findByTime(ZonedDateTime time) {
        LOG.trace(".findByTime()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.time.eq(time)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<DataVersion> findByLock(Boolean lock) {
        LOG.trace(".findByLock()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.lock.eq(lock)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<DataVersion> findByLockTime(ZonedDateTime lockTime) {
        LOG.trace(".findByLockTime()");
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        return factory.select(qDataVersion).from(qDataVersion).where(qDataVersion.lockTime.eq(lockTime)).orderBy(qDataVersion.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<DataVersion> findDataVersion() {
        final QDataVersion qDataVersion = QDataVersion.dataVersion;
        final AbstractJPAQuery<DataVersion, ?> select = (AbstractJPAQuery<DataVersion, ?>) factory.select(qDataVersion);
        select.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return Optional.ofNullable(select.from(qDataVersion).fetchOne());
    }

    @Override
    public List<DataVersion> findAllById(Collection<DataVersionId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QDataVersion.dataVersion)
                .where(QDataVersion.dataVersion.id.in(ids.stream().map(DataVersionId::getValue).toList()))
                .fetch();
    }
}
