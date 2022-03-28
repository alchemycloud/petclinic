package co.aleksa.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.VetId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateVisitRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<VetId> VET_ID = new PropertyPath<>("vetId");
    public static final PropertyPath<PetId> PET_ID = new PropertyPath<>("petId");
    public static final PropertyPath<Integer> VISIT_NUMBER = new PropertyPath<>("visitNumber");
    public static final PropertyPath<ZonedDateTime> TIMESTAMP = new PropertyPath<>("timestamp");
    public static final PropertyPath<BigDecimal> PET_WEIGHT = new PropertyPath<>("petWeight");
    public static final PropertyPath<String> DESCRIPTION = new PropertyPath<>("description");
    public static final PropertyPath<Boolean> SCHEDULED = new PropertyPath<>("scheduled");

    @NotNull private VetId vetId;

    @NotNull private PetId petId;

    @NotNull private Integer visitNumber;

    @NotNull private ZonedDateTime timestamp;

    private BigDecimal petWeight;

    @Size(max = 1024)
    private String description;

    @NotNull private Boolean scheduled;

    private CreateVisitRequest() {}

    public CreateVisitRequest(
            VetId vetId, PetId petId, Integer visitNumber, ZonedDateTime timestamp, BigDecimal petWeight, String description, Boolean scheduled) {
        this();
        this.vetId = vetId;
        this.petId = petId;
        this.visitNumber = visitNumber;
        this.timestamp = timestamp;
        this.petWeight = petWeight;
        this.description = description;
        this.scheduled = scheduled;
    }

    public final VetId getVetId() {
        return vetId;
    }

    public final CreateVisitRequest setVetId(VetId vetId) {
        this.vetId = vetId;
        return this;
    }

    public final PetId getPetId() {
        return petId;
    }

    public final CreateVisitRequest setPetId(PetId petId) {
        this.petId = petId;
        return this;
    }

    public final Integer getVisitNumber() {
        return visitNumber;
    }

    public final CreateVisitRequest setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
        return this;
    }

    public final ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public final CreateVisitRequest setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public final BigDecimal getPetWeight() {
        return petWeight;
    }

    public final CreateVisitRequest setPetWeight(BigDecimal petWeight) {
        this.petWeight = petWeight;
        return this;
    }

    public final String getDescription() {
        return description;
    }

    public final CreateVisitRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public final Boolean getScheduled() {
        return scheduled;
    }

    public final CreateVisitRequest setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (CreateVisitRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.vetId == null) ? 0 : this.vetId.hashCode());
        result = PRIME * result + ((this.petId == null) ? 0 : this.petId.hashCode());
        result = PRIME * result + ((this.visitNumber == null) ? 0 : this.visitNumber.hashCode());
        result = PRIME * result + ((this.timestamp == null) ? 0 : this.timestamp.hashCode());
        result = PRIME * result + ((this.petWeight == null) ? 0 : this.petWeight.hashCode());
        result = PRIME * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = PRIME * result + ((this.scheduled == null) ? 0 : this.scheduled.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CreateVisitRequest["
                + "this.vetId="
                + this.vetId
                + ", this.petId="
                + this.petId
                + ", this.visitNumber="
                + this.visitNumber
                + ", this.timestamp="
                + this.timestamp
                + ", this.petWeight="
                + this.petWeight
                + ", this.description="
                + this.description
                + ", this.scheduled="
                + this.scheduled
                + "]";
    }
}
