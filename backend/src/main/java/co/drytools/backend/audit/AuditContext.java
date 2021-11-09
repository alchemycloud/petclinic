package co.drytools.backend.audit;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.audit.changedtos.UpdatedFieldsDto;
import co.drytools.backend.audit.changedtos.ValueDeltaDto;
import co.drytools.backend.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class AuditContext {

    private final Map<User, UpdatedFieldsDto> userChanges = new HashMap<>();

    public <U> void register(User user, AuditType auditType, Optional<PropertyPath> propertyPath, Optional<U> newValue) {
        if (user.getId() == null) {
            return;
        }

        registerPropertyValue(userChanges, user, auditType, propertyPath, newValue, User::auditMapper);
    }

    public <T, U> void registerPropertyValue(
            Map<T, UpdatedFieldsDto> map,
            T object,
            AuditType type,
            Optional<PropertyPath> propertyPath,
            Optional<U> newValue,
            Function<T, Map<PropertyPath, ValueDeltaDto>> auditMapper) {

        final boolean previouslyUnregistered = !map.containsKey(object);

        if (previouslyUnregistered) {
            final UpdatedFieldsDto newDto = new UpdatedFieldsDto(type, auditMapper.apply(object));
            map.put(object, newDto);
        }

        final UpdatedFieldsDto updatedFieldsDto = map.get(object);

        switch (updatedFieldsDto.getAuditType()) {
            case CREATE:
                switch (type) {
                    case CREATE:
                        // do nothing
                        break;
                    case UPDATE:
                        // create only registers entity but no values, fields are submitted via setter=update
                        // set only new value
                        updatedFieldsDto.registerFieldValue(propertyPath.get(), newValue.orElse(null));
                        break;
                    case DELETE:
                        // create then delete, is like nothing has happened
                        map.remove(object);
                        break;
                }
                break;
            case UPDATE:
                switch (type) {
                    case CREATE:
                        throw new IllegalStateException("This cant happen");
                    case UPDATE:
                        // overwrite the last value
                        // set only new value
                        updatedFieldsDto.registerFieldValue(propertyPath.get(), newValue.orElse(null));
                        break;
                    case DELETE:
                        // update then delete, delete is stronger
                        final UpdatedFieldsDto newDto = new UpdatedFieldsDto(type, auditMapper.apply(object));
                        map.put(object, newDto);
                        break;
                }
                break;
            case DELETE:
                switch (type) {
                    case CREATE:
                        // delete then create, create is stronger
                        final UpdatedFieldsDto newDto = new UpdatedFieldsDto(type, auditMapper.apply(object));
                        map.put(object, newDto);
                        break;
                    case DELETE:
                        if (previouslyUnregistered) {
                            // do nothing
                            break;
                        }
                    case UPDATE:
                        throw new IllegalStateException("This cant happen");
                }
                break;
        }
    }

    public Map<User, UpdatedFieldsDto> getUserChanges() {
        return userChanges;
    }

    public boolean isDirty() {
        return !userChanges.isEmpty();
    }
}
