package co.drytools.backend.api.dto.petapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.PetType;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FindPetbyTypeResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull private PetType petType;

    private FindPetbyTypeResponse() {}

    public FindPetbyTypeResponse(String name, PetType petType) {
        this();
        this.name = name;
        this.petType = petType;
    }

    public final String getName() {
        return name;
    }

    public final FindPetbyTypeResponse setName(String name) {
        this.name = name;
        return this;
    }

    public final PetType getPetType() {
        return petType;
    }

    public final FindPetbyTypeResponse setPetType(PetType petType) {
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
        if (FindPetbyTypeResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.petType == null) ? 0 : this.petType.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FindPetbyTypeResponse[" + "this.name=" + this.name + ", this.petType=" + this.petType + "]";
    }
}
