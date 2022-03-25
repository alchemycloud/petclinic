package co.aleksa.administration.repository;

import co.aleksa.administration.model.DataVersion;
import co.aleksa.administration.model.id.DataVersionId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface DataVersionRepository extends SimpleRepository<DataVersion, DataVersionId> {

    DataVersion create(Integer major, Integer minor, Integer revision, Integer number, ZonedDateTime time, Boolean lock, ZonedDateTime lockTime);

    List<DataVersion> findByMajor(Integer major);

    List<DataVersion> findByMinor(Integer minor);

    List<DataVersion> findByRevision(Integer revision);

    List<DataVersion> findByNumber(Integer number);

    List<DataVersion> findByTime(ZonedDateTime time);

    List<DataVersion> findByLock(Boolean lock);

    List<DataVersion> findByLockTime(ZonedDateTime lockTime);

    Optional<DataVersion> findDataVersion();
}
