package co.aleksa.administration.api.dto.userapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class DeleteUserRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> ID = new PropertyPath<>("id");

    @NotNull private UserId id;

    private DeleteUserRequest() {}

    public DeleteUserRequest(UserId id) {
        this();
        this.id = id;
    }

    public final UserId getId() {
        return id;
    }

    public final DeleteUserRequest setId(UserId id) {
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
        if (DeleteUserRequest.class != obj.getClass()) {
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
        return "DeleteUserRequest[" + "this.id=" + this.id + "]";
    }
}
