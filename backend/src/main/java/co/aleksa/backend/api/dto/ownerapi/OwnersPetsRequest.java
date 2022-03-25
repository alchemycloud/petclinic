package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.OwnerId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class OwnersPetsRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> OWNER_ID = new PropertyPath<>("ownerId");

    @NotNull private OwnerId ownerId;

    private OwnersPetsRequest() {}

    public OwnersPetsRequest(OwnerId ownerId) {
        this();
        this.ownerId = ownerId;
    }

    public final OwnerId getOwnerId() {
        return ownerId;
    }

    public final OwnersPetsRequest setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
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
        if (OwnersPetsRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.ownerId == null) ? 0 : this.ownerId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "OwnersPetsRequest[" + "this.ownerId=" + this.ownerId + "]";
    }
}
