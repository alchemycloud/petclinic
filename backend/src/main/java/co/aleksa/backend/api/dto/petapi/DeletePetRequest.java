package co.aleksa.backend.api.dto.petapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.PetId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class DeletePetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");

    @NotNull private PetId id;

    private DeletePetRequest() {}

    public DeletePetRequest(PetId id) {
        this();
        this.id = id;
    }

    public final PetId getId() {
        return id;
    }

    public final DeletePetRequest setId(PetId id) {
        this.id = id;
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
        if (DeletePetRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "DeletePetRequest[" + "this.id=" + this.id + "]";
    }
}
