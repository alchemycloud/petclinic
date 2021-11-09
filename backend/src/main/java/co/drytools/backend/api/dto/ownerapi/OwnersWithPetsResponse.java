package co.drytools.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OwnersWithPetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> USER_FIRST_NAME = new PropertyPath<>("userFirstName");
    public static final PropertyPath<String> USER_LAST_NAME = new PropertyPath<>("userLastName");

    @NotNull
    @Size(min = 1, max = 40)
    private String userFirstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String userLastName;

    private OwnersWithPetsResponse() {}

    public OwnersWithPetsResponse(String userFirstName, String userLastName) {
        this();
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public final String getUserFirstName() {
        return userFirstName;
    }

    public final OwnersWithPetsResponse setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public final String getUserLastName() {
        return userLastName;
    }

    public final OwnersWithPetsResponse setUserLastName(String userLastName) {
        this.userLastName = userLastName;
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
        if (OwnersWithPetsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.userFirstName == null) ? 0 : this.userFirstName.hashCode());
        result = PRIME * result + ((this.userLastName == null) ? 0 : this.userLastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "OwnersWithPetsResponse[" + "this.userFirstName=" + this.userFirstName + ", this.userLastName=" + this.userLastName + "]";
    }
}
