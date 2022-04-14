package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.OwnerId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EnrichedOwnerDTO implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");

    @NotNull private OwnerId id;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    private EnrichedOwnerDTO() {}

    public EnrichedOwnerDTO(OwnerId id, String email, String firstName, String lastName) {
        this();
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public final OwnerId getId() {
        return id;
    }

    public final EnrichedOwnerDTO setId(OwnerId id) {
        this.id = id;
        return this;
    }

    public final String getEmail() {
        return email;
    }

    public final EnrichedOwnerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final EnrichedOwnerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public final String getLastName() {
        return lastName;
    }

    public final EnrichedOwnerDTO setLastName(String lastName) {
        this.lastName = lastName;
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
        if (EnrichedOwnerDTO.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "EnrichedOwnerDTO["
                + "this.id="
                + this.id
                + ", this.email="
                + this.email
                + ", this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + "]";
    }
}
