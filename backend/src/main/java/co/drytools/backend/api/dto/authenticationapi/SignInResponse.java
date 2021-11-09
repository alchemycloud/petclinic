package co.drytools.backend.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignInResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> ACCESS_TOKEN = new PropertyPath<>("accessToken");
    public static final PropertyPath<String> REFRESH_TOKEN = new PropertyPath<>("refreshToken");
    public static final PropertyPath<UserId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<ZonedDateTime> BIRTHDATE = new PropertyPath<>("birthdate");
    public static final PropertyPath<Boolean> ACTIVE = new PropertyPath<>("active");
    public static final PropertyPath<UserRole> ROLE = new PropertyPath<>("role");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");

    @NotNull
    @Size(min = 64, max = 4096)
    private String accessToken;

    @NotNull
    @Size(min = 64, max = 4096)
    private String refreshToken;

    @NotNull private UserId id;

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    @NotNull private ZonedDateTime birthdate;

    @NotNull private Boolean active;

    @NotNull private UserRole role;

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    private SignInResponse() {}

    public SignInResponse(
            String accessToken,
            String refreshToken,
            UserId id,
            String firstName,
            String lastName,
            ZonedDateTime birthdate,
            Boolean active,
            UserRole role,
            String email) {
        this();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.active = active;
        this.role = role;
        this.email = email;
    }

    public final String getAccessToken() {
        return accessToken;
    }

    public final SignInResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public final String getRefreshToken() {
        return refreshToken;
    }

    public final SignInResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public final UserId getId() {
        return id;
    }

    public final SignInResponse setId(UserId id) {
        this.id = id;
        return this;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final SignInResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public final String getLastName() {
        return lastName;
    }

    public final SignInResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public final ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public final SignInResponse setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public final Boolean getActive() {
        return active;
    }

    public final SignInResponse setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public final UserRole getRole() {
        return role;
    }

    public final SignInResponse setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public final String getEmail() {
        return email;
    }

    public final SignInResponse setEmail(String email) {
        this.email = email;
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
        if (SignInResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.accessToken == null) ? 0 : this.accessToken.hashCode());
        result = PRIME * result + ((this.refreshToken == null) ? 0 : this.refreshToken.hashCode());
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        result = PRIME * result + ((this.birthdate == null) ? 0 : this.birthdate.hashCode());
        result = PRIME * result + ((this.active == null) ? 0 : this.active.hashCode());
        result = PRIME * result + ((this.role == null) ? 0 : this.role.hashCode());
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SignInResponse["
                + "this.id="
                + this.id
                + ", this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + ", this.birthdate="
                + this.birthdate
                + ", this.active="
                + this.active
                + ", this.role="
                + this.role
                + ", this.email="
                + this.email
                + "]";
    }
}
