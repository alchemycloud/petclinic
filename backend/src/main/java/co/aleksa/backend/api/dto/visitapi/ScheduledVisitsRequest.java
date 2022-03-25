package co.aleksa.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ScheduledVisitsRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<Integer> DROP = new PropertyPath<>("drop");
    public static final PropertyPath<Integer> TAKE = new PropertyPath<>("take");

    @NotNull
    @Min(0)
    private Integer drop;

    @NotNull
    @Min(0)
    private Integer take;

    private ScheduledVisitsRequest() {}

    public ScheduledVisitsRequest(Integer drop, Integer take) {
        this();
        this.drop = drop;
        this.take = take;
    }

    public final Integer getDrop() {
        return drop;
    }

    public final ScheduledVisitsRequest setDrop(Integer drop) {
        this.drop = drop;
        return this;
    }

    public final Integer getTake() {
        return take;
    }

    public final ScheduledVisitsRequest setTake(Integer take) {
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
        if (ScheduledVisitsRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.drop == null) ? 0 : this.drop.hashCode());
        result = PRIME * result + ((this.take == null) ? 0 : this.take.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ScheduledVisitsRequest[" + "this.drop=" + this.drop + ", this.take=" + this.take + "]";
    }
}
