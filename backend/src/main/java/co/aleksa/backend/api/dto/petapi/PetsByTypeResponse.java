package co.aleksa.backend.api.dto.petapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.PetId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetsByTypeResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");

    @NotNull private PetId id;

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    private PetsByTypeResponse() {}

    public PetsByTypeResponse(PetId id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public final PetId getId() {
        return id;
    }

    public final PetsByTypeResponse setId(PetId id) {
        this.id = id;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final PetsByTypeResponse setName(String name) {
        this.name = name;
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
        if (PetsByTypeResponse.class != obj.getClass()) {
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
        return result;
    }

    @Override
    public String toString() {
        return "PetsByTypeResponse[" + "this.id=" + this.id + ", this.name=" + this.name + "]";
    }
}
