package co.drytools.backend.repository;

import co.drytools.backend.model.id.AbstractId;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SimpleRepository<T, Id extends AbstractId> {

    NumberPath<Long> getEntityIdPathBase();

    <S extends T> S update(S entity);

    T getOne(Id id);

    T getOneAndLock(Id id);

    Optional<T> findById(Id id);

    List<T> findAllById(Collection<Id> ids);

    List<T> findAll();

    void delete(T entity);

    void deleteById(Id id);

    void deleteAll(Collection<T> entities);

    void deleteAll();
}
