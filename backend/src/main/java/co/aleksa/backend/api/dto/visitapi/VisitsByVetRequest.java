package co.aleksa.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.VetId;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VisitsByVetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<VetId> VET_ID = new PropertyPath<>("vetId");
    public static final PropertyPath<Integer> DROP = new PropertyPath<>("drop");
    public static final PropertyPath<Integer> TAKE = new PropertyPath<>("take");

    @NotNull private VetId vetId;

    @NotNull
    @Min(0)
    private Integer drop;

    @NotNull
    @Min(0)
    private Integer take;

    private VisitsByVetRequest() {}

    public VisitsByVetRequest(VetId vetId, Integer drop, Integer take) {
        this();
        this.vetId = vetId;
        this.drop = drop;
        this.take = take;
    }

    public final VetId getVetId() {
        return vetId;
    }

    public final VisitsByVetRequest setVetId(VetId vetId) {
        this.vetId = vetId;
        return this;
    }

    public final Integer getDrop() {
        return drop;
    }

    public final VisitsByVetRequest setDrop(Integer drop) {
        this.drop = drop;
        return this;
    }

    public final Integer getTake() {
        return take;
    }

    public final VisitsByVetRequest setTake(Integer take) {
        this.take = take;
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
        if (VisitsByVetRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.vetId == null) ? 0 : this.vetId.hashCode());
        result = PRIME * result + ((this.drop == null) ? 0 : this.drop.hashCode());
        result = PRIME * result + ((this.take == null) ? 0 : this.take.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VisitsByVetRequest[" + "this.vetId=" + this.vetId + ", this.drop=" + this.drop + ", this.take=" + this.take + "]";
    }
}
