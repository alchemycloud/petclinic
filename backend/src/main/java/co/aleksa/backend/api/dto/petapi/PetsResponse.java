package co.aleksa.backend.api.dto.petapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<String> OWNER_LAST_NAME = new PropertyPath<>("ownerLastName");

    @NotNull private PetId id;

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull private PetType petType;

    @NotNull
    @Size(min = 1, max = 60)
    private String ownerLastName;

    private PetsResponse() {}

    public PetsResponse(PetId id, String name, PetType petType, String ownerLastName) {
        this();
        this.id = id;
        this.name = name;
        this.petType = petType;
        this.ownerLastName = ownerLastName;
    }

    public final PetId getId() {
        return id;
    }

    public final PetsResponse setId(PetId id) {
        this.id = id;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final PetsResponse setName(String name) {
        this.name = name;
        return this;
    }

    public final PetType getPetType() {
        return petType;
    }

    public final PetsResponse setPetType(PetType petType) {
        this.petType = petType;
        return this;
    }

    public final String getOwnerLastName() {
        return ownerLastName;
    }

    public final PetsResponse setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
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
        if (PetsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.petType == null) ? 0 : this.petType.hashCode());
        result = PRIME * result + ((this.ownerLastName == null) ? 0 : this.ownerLastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "PetsResponse["
                + "this.id="
                + this.id
                + ", this.name="
                + this.name
                + ", this.petType="
                + this.petType
                + ", this.ownerLastName="
                + this.ownerLastName
                + "]";
    }
}
