package co.aleksa.administration.api.dto.userapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class UserActivationDTO implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Boolean> ACTIVE = new PropertyPath<>("active");

    @NotNull private UserId id;

    @NotNull private Boolean active;

    private UserActivationDTO() {}

    public UserActivationDTO(UserId id, Boolean active) {
        this();
        this.id = id;
        this.active = active;
    }

    public final UserId getId() {
        return id;
    }

    public final UserActivationDTO setId(UserId id) {
        this.id = id;
        return this;
    }

    public final Boolean getActive() {
        return active;
    }

    public final UserActivationDTO setActive(Boolean active) {
        this.active = active;
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
        if (UserActivationDTO.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.active == null) ? 0 : this.active.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UserActivationDTO[" + "this.id=" + this.id + ", this.active=" + this.active + "]";
    }
}
