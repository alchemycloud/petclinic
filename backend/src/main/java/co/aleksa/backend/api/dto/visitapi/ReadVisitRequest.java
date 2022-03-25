package co.aleksa.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.VisitId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class ReadVisitRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<VisitId> ID = new PropertyPath<>("id");

    @NotNull private VisitId id;

    private ReadVisitRequest() {}

    public ReadVisitRequest(VisitId id) {
        this();
        this.id = id;
    }

    public final VisitId getId() {
        return id;
    }

    public final ReadVisitRequest setId(VisitId id) {
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
        if (ReadVisitRequest.class != obj.getClass()) {
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
        return "ReadVisitRequest[" + "this.id=" + this.id + "]";
    }
}
