package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.PetId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OwnersPetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<OwnerId> OWNER_ID = new PropertyPath<>("ownerId");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");

    @NotNull private PetId id;

    @NotNull private OwnerId ownerId;

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    private OwnersPetsResponse() {}

    public OwnersPetsResponse(PetId id, OwnerId ownerId, String name) {
        this();
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

    public final PetId getId() {
        return id;
    }

    public final OwnersPetsResponse setId(PetId id) {
        this.id = id;
        return this;
    }

    public final OwnerId getOwnerId() {
        return ownerId;
    }

    public final OwnersPetsResponse setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final OwnersPetsResponse setName(String name) {
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
        if (OwnersPetsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.ownerId == null) ? 0 : this.ownerId.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "OwnersPetsResponse[" + "this.id=" + this.id + ", this.ownerId=" + this.ownerId + ", this.name=" + this.name + "]";
    }
}
