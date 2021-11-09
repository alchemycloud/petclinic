package co.drytools.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.OwnerId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OwnersForAddressResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> USER_EMAIL = new PropertyPath<>("userEmail");
    public static final PropertyPath<String> USER_FIRST_NAME = new PropertyPath<>("userFirstName");
    public static final PropertyPath<String> USER_LAST_NAME = new PropertyPath<>("userLastName");

    @NotNull private OwnerId id;

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String userEmail;

    @NotNull
    @Size(min = 1, max = 40)
    private String userFirstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String userLastName;

    private OwnersForAddressResponse() {}

    public OwnersForAddressResponse(OwnerId id, String userEmail, String userFirstName, String userLastName) {
        this();
        this.id = id;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public final OwnerId getId() {
        return id;
    }

    public final OwnersForAddressResponse setId(OwnerId id) {
        this.id = id;
        return this;
    }

    public final String getUserEmail() {
        return userEmail;
    }

    public final OwnersForAddressResponse setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public final String getUserFirstName() {
        return userFirstName;
    }

    public final OwnersForAddressResponse setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public final String getUserLastName() {
        return userLastName;
    }

    public final OwnersForAddressResponse setUserLastName(String userLastName) {
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
        if (OwnersForAddressResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.userEmail == null) ? 0 : this.userEmail.hashCode());
        result = PRIME * result + ((this.userFirstName == null) ? 0 : this.userFirstName.hashCode());
        result = PRIME * result + ((this.userLastName == null) ? 0 : this.userLastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "OwnersForAddressResponse["
                + "this.id="
                + this.id
                + ", this.userEmail="
                + this.userEmail
                + ", this.userFirstName="
                + this.userFirstName
                + ", this.userLastName="
                + this.userLastName
                + "]";
    }
}
