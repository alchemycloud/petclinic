package co.aleksa.administration.audit.changedtos;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.audit.AuditType;
import java.util.HashMap;
import java.util.Map;

public class UpdatedFieldsDto {

    private final AuditType auditType;

    Map<PropertyPath, ValueDeltaDto> updatedFields; // Original vs Update

    public UpdatedFieldsDto(AuditType auditType, Map<PropertyPath, ValueDeltaDto> fields) {
        this.auditType = auditType;
        this.updatedFields = fields;
    }

    public UpdatedFieldsDto(AuditType auditType) {
        this.auditType = auditType;
        this.updatedFields = new HashMap<>();
    }

    // Cannot change audit type
    // If it's created as CREATE and gets new fields in updates, until it's flushed it's a new creation
    public AuditType getAuditType() {
        return auditType;
    }

    public <U> void registerFieldValue(PropertyPath propertyPath, U newValue) {
        if (!updatedFields.containsKey(propertyPath)) {
            throw new IllegalStateException("Cannot register value to empty original map");
        } else {
            final ValueDeltaDto originalAndUpdatePair = updatedFields.get(propertyPath);
            final Object original = originalAndUpdatePair.getOriginal();

            if (original == null || !original.equals(newValue)) {
                originalAndUpdatePair.setUpdate(newValue);
            } else {
                originalAndUpdatePair.setUpdate(original);
            }

            updatedFields.put(propertyPath, originalAndUpdatePair);
        }
    }

    public boolean isDirty() {
        return updatedFields.values().stream().anyMatch(valueDeltaDto -> valueDeltaDto.getUpdate() != valueDeltaDto.getOriginal());
    }
}
