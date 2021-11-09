package co.drytools.backend.api.dto.petapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.PetType;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class FindPetbyTypeRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");

    @NotNull private PetType petType;

    private FindPetbyTypeRequest() {}

    public FindPetbyTypeRequest(PetType petType) {
        this();
        this.petType = petType;
    }

    public final PetType getPetType() {
        return petType;
    }

    public final FindPetbyTypeRequest setPetType(PetType petType) {
        this.petType = petType;
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
        if (FindPetbyTypeRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.petType == null) ? 0 : this.petType.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FindPetbyTypeRequest[" + "this.petType=" + this.petType + "]";
    }
}
