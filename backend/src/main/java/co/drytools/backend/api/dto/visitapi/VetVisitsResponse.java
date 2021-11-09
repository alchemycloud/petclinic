package co.drytools.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VetVisitsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> VET_USER_ID = new PropertyPath<>("vetUserId");
    public static final PropertyPath<String> PET_NAME = new PropertyPath<>("petName");
    public static final PropertyPath<Integer> VISIT_NUMBER = new PropertyPath<>("visitNumber");
    public static final PropertyPath<Boolean> SCHEDULED = new PropertyPath<>("scheduled");

    @NotNull private UserId vetUserId;

    @NotNull
    @Size(min = 2, max = 40)
    private String petName;

    @NotNull private Integer visitNumber;

    @NotNull private Boolean scheduled;

    private VetVisitsResponse() {}

    public VetVisitsResponse(UserId vetUserId, String petName, Integer visitNumber, Boolean scheduled) {
        this();
        this.vetUserId = vetUserId;
        this.petName = petName;
        this.visitNumber = visitNumber;
        this.scheduled = scheduled;
    }

    public final UserId getVetUserId() {
        return vetUserId;
    }

    public final VetVisitsResponse setVetUserId(UserId vetUserId) {
        this.vetUserId = vetUserId;
        return this;
    }

    public final String getPetName() {
        return petName;
    }

    public final VetVisitsResponse setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public final Integer getVisitNumber() {
        return visitNumber;
    }

    public final VetVisitsResponse setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
        return this;
    }

    public final Boolean getScheduled() {
        return scheduled;
    }

    public final VetVisitsResponse setScheduled(Boolean scheduled) {
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
        if (VetVisitsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.vetUserId == null) ? 0 : this.vetUserId.hashCode());
        result = PRIME * result + ((this.petName == null) ? 0 : this.petName.hashCode());
        result = PRIME * result + ((this.visitNumber == null) ? 0 : this.visitNumber.hashCode());
        result = PRIME * result + ((this.scheduled == null) ? 0 : this.scheduled.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VetVisitsResponse["
                + "this.vetUserId="
                + this.vetUserId
                + ", this.petName="
                + this.petName
                + ", this.visitNumber="
                + this.visitNumber
                + ", this.scheduled="
                + this.scheduled
                + "]";
    }
}
