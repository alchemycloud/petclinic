package co.aleksa.backend.repository;

import co.aleksa.backend.model.id.AbstractId;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

public abstract class AbstractSimpleRepositoryImpl<T, Id extends AbstractId> implements SimpleRepository<T, Id> {

    @Inject protected JPQLQueryFactory factory;

    @Inject protected EntityManager entityManager;

    protected abstract Class<T> getDomainClass();

    protected abstract EntityPath<T> getEntityPathBase();

    protected abstract Id getId(T entity);

    @Override
    public <S extends T> S update(S entity) {
        if (getId(entity) == null) {
            throw new IllegalStateException("Use create method");
        }
        entityManager.persist(entity);
        postUpdate(entity);
        return entity;
    }

    protected <S extends T> void postSave(S entity) {}

    protected <S extends T> void postUpdate(S entity) {}

    @Override
    public T getOne(Id id) {
        return entityManager.find(getDomainClass(), id.getValue());
    }

    @Override
    public T getOneAndLock(Id id) {
        return entityManager.find(getDomainClass(), id.getValue(), LockModeType.PESSIMISTIC_WRITE);
    }

    @Override
    public Optional<T> findById(Id id) {
        return Optional.ofNullable(entityManager.find(getDomainClass(), id.getValue()));
    }

    @Override
    public List<T> findAll() {
        return factory.select(getEntityPathBase()).from(getEntityPathBase()).orderBy(getEntityIdPathBase().asc()).fetch();
    }

    protected void preDelete(Collection<T> entities) {}

    @Override
    public void delete(T entity) {
        deleteAll(Collections.singleton(entity));
    }

    @Override
    public void deleteById(Id id) {
        final T entity = getOne(id);
        delete(entity);
    }

    @Override
    public void deleteAll(Collection<T> toBeDeleted) {
        if (toBeDeleted.isEmpty()) return;

        final HashSet<T> entities = new HashSet<>(toBeDeleted);
        preDelete(entities);

        for (T entity : entities) {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        }

        entityManager.flush();
    }

    @Override
    public void deleteAll() {
        deleteAll(findAll());
    }
}
