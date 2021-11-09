package co.drytools.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VetVisitsRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<Integer> DROP = new PropertyPath<>("drop");
    public static final PropertyPath<Integer> TAKE = new PropertyPath<>("take");

    @NotNull private UserId userId;

    @NotNull
    @Min(0)
    private Integer drop;

    @NotNull
    @Min(0)
    private Integer take;

    private VetVisitsRequest() {}

    public VetVisitsRequest(UserId userId, Integer drop, Integer take) {
        this();
        this.userId = userId;
        this.drop = drop;
        this.take = take;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final VetVisitsRequest setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final Integer getDrop() {
        return drop;
    }

    public final VetVisitsRequest setDrop(Integer drop) {
        this.drop = drop;
        return this;
    }

    public final Integer getTake() {
        return take;
    }

    public final VetVisitsRequest setTake(Integer take) {
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
        if (VetVisitsRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = PRIME * result + ((this.drop == null) ? 0 : this.drop.hashCode());
        result = PRIME * result + ((this.take == null) ? 0 : this.take.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VetVisitsRequest[" + "this.userId=" + this.userId + ", this.drop=" + this.drop + ", this.take=" + this.take + "]";
    }
}
