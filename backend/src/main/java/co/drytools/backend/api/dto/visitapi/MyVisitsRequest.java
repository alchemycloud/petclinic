package co.drytools.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class MyVisitsRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> USER_ID_ID = new PropertyPath<>("userIdId");

    @NotNull private UserId userIdId;

    private MyVisitsRequest() {}

    public MyVisitsRequest(UserId userIdId) {
        this();
        this.userIdId = userIdId;
    }

    public final UserId getUserIdId() {
        return userIdId;
    }

    public final MyVisitsRequest setUserIdId(UserId userIdId) {
        this.userIdId = userIdId;
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
        if (MyVisitsRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.userIdId == null) ? 0 : this.userIdId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MyVisitsRequest[" + "this.userIdId=" + this.userIdId + "]";
    }
}
