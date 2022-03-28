package co.aleksa.backend.audit;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.audit.changedtos.UpdatedFieldsDto;
import co.aleksa.backend.audit.changedtos.ValueDeltaDto;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class AuditContext {

    private final Map<User, UpdatedFieldsDto> userChanges = new HashMap<>();

    private final Map<Pet, UpdatedFieldsDto> petChanges = new HashMap<>();

    public <U> void register(User user, AuditType auditType, Optional<PropertyPath> propertyPath, Optional<U> newValue) {
        if (user.getId() == null) {
            return;
        }

        registerPropertyValue(userChanges, user, auditType, propertyPath, newValue, User::auditMapper);
    }

    public <U> void register(Pet pet, AuditType auditType, Optional<PropertyPath> propertyPath, Optional<U> newValue) {
        if (pet.getId() == null) {
            return;
        }

        registerPropertyValue(petChanges, pet, auditType, propertyPath, newValue, Pet::auditMapper);
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
            case CREATE -> {
                switch (type) {
                    case CREATE -> {}
                    case UPDATE -> updatedFieldsDto.registerFieldValue(propertyPath.get(), newValue.orElse(null));
                    case DELETE -> map.remove(object);
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                }
            }
            case UPDATE -> {
                switch (type) {
                    case CREATE -> throw new IllegalStateException("This cant happen");
                    case UPDATE -> // overwrite the last value, set only new value
                    updatedFieldsDto.registerFieldValue(propertyPath.get(), newValue.orElse(null));
                    case DELETE -> // update then delete, delete is stronger
                    map.put(object, new UpdatedFieldsDto(type, auditMapper.apply(object)));
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                }
            }
            case DELETE -> {
                switch (type) {
                    case CREATE -> // delete then create, create is stronger
                    map.put(object, new UpdatedFieldsDto(type, auditMapper.apply(object)));
                    case DELETE -> {
                        if (!previouslyUnregistered) {
                            throw new IllegalStateException("This cant happen");
                        }
                        // do nothing
                    }
                    case UPDATE -> throw new IllegalStateException("This cant happen");
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                }
            }
        }
    }

    public Map<User, UpdatedFieldsDto> getUserChanges() {
        return userChanges;
    }

    public Map<Pet, UpdatedFieldsDto> getPetChanges() {
        return petChanges;
    }

    public boolean isDirty() {
        return !userChanges.isEmpty() || !petChanges.isEmpty();
    }
}
