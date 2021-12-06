package co.drytools.backend.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<ZonedDateTime> BIRTHDATE = new PropertyPath<>("birthdate");
    public static final PropertyPath<Boolean> ACTIVE = new PropertyPath<>("active");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<String> PASSWORD = new PropertyPath<>("password");

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    @NotNull private ZonedDateTime birthdate;

    @NotNull private Boolean active;

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    @NotNull
    @Size(min = 12, max = 128)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){12,128}$")
    private String password;

    private SignUpRequest() {}

    public SignUpRequest(String firstName, String lastName, ZonedDateTime birthdate, Boolean active, String email, String password) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.active = active;
        this.email = email;
        this.password = password;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final SignUpRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public final String getLastName() {
        return lastName;
    }

    public final SignUpRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public final ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public final SignUpRequest setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public final Boolean getActive() {
        return active;
    }

    public final SignUpRequest setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public final String getEmail() {
        return email;
    }

    public final SignUpRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public final String getPassword() {
        return password;
    }

    public final SignUpRequest setPassword(String password) {
        this.password = password;
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
        if (SignUpRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        result = PRIME * result + ((this.birthdate == null) ? 0 : this.birthdate.hashCode());
        result = PRIME * result + ((this.active == null) ? 0 : this.active.hashCode());
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = PRIME * result + ((this.password == null) ? 0 : this.password.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SignUpRequest["
                + "this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + ", this.birthdate="
                + this.birthdate
                + ", this.active="
                + this.active
                + ", this.email="
                + this.email
                + "]";
    }
}
