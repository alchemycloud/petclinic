package co.aleksa.administration.api.dto.userapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class GetActiveUserRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");

    @NotNull private UserId userId;

    private GetActiveUserRequest() {}

    public GetActiveUserRequest(UserId userId) {
        this();
        this.userId = userId;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final GetActiveUserRequest setUserId(UserId userId) {
        this.userId = userId;
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
        if (GetActiveUserRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "GetActiveUserRequest[" + "this.userId=" + this.userId + "]";
    }
}
