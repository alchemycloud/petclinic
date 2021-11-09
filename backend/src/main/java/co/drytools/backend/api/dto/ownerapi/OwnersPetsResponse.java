package co.drytools.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OwnersPetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetId> PET_ID = new PropertyPath<>("petId");
    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> PET_NAME = new PropertyPath<>("petName");

    @NotNull private PetId petId;

    @NotNull private OwnerId id;

    @NotNull
    @Size(min = 2, max = 40)
    private String petName;

    private OwnersPetsResponse() {}

    public OwnersPetsResponse(PetId petId, OwnerId id, String petName) {
        this();
        this.petId = petId;
        this.id = id;
        this.petName = petName;
    }

    public final PetId getPetId() {
        return petId;
    }

    public final OwnersPetsResponse setPetId(PetId petId) {
        this.petId = petId;
        return this;
    }

    public final OwnerId getId() {
        return id;
    }

    public final OwnersPetsResponse setId(OwnerId id) {
        this.id = id;
        return this;
    }

    public final String getPetName() {
        return petName;
    }

    public final OwnersPetsResponse setPetName(String petName) {
        this.petName = petName;
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
        if (OwnersPetsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.petId == null) ? 0 : this.petId.hashCode());
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.petName == null) ? 0 : this.petName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "OwnersPetsResponse[" + "this.petId=" + this.petId + ", this.id=" + this.id + ", this.petName=" + this.petName + "]";
    }
}
