package co.drytools.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.OwnerId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class DeleteOwnerRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");

    @NotNull private OwnerId id;

    private DeleteOwnerRequest() {}

    public DeleteOwnerRequest(OwnerId id) {
        this();
        this.id = id;
    }

    public final OwnerId getId() {
        return id;
    }

    public final DeleteOwnerRequest setId(OwnerId id) {
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
        if (DeleteOwnerRequest.class != obj.getClass()) {
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
        return "DeleteOwnerRequest[" + "this.id=" + this.id + "]";
    }
}
