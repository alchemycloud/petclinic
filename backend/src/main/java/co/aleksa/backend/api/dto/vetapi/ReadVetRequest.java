package co.aleksa.backend.api.dto.vetapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.VetId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class ReadVetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<VetId> ID = new PropertyPath<>("id");

    @NotNull private VetId id;

    private ReadVetRequest() {}

    public ReadVetRequest(VetId id) {
        this();
        this.id = id;
    }

    public final VetId getId() {
        return id;
    }

    public final ReadVetRequest setId(VetId id) {
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
        if (ReadVetRequest.class != obj.getClass()) {
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
        return "ReadVetRequest[" + "this.id=" + this.id + "]";
    }
}
