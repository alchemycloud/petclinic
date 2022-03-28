package co.aleksa.administration.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.enumeration.UserRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignInResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> ACCESS_TOKEN = new PropertyPath<>("accessToken");
    public static final PropertyPath<String> REFRESH_TOKEN = new PropertyPath<>("refreshToken");
    public static final PropertyPath<List<SignInResponseTokens>> TOKENS = new PropertyPath<>("tokens");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<UserRole> ROLE = new PropertyPath<>("role");
    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<List<SignInResponseTenants>> TENANTS = new PropertyPath<>("tenants");

    @NotNull
    @Size(min = 64, max = 4096)
    private String accessToken;

    @NotNull
    @Size(min = 64, max = 4096)
    private String refreshToken;

    @Valid private List<SignInResponseTokens> tokens = new ArrayList<>();

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    @NotNull private UserRole role;

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    @Valid private List<SignInResponseTenants> tenants = new ArrayList<>();

    private SignInResponse() {}

    public SignInResponse(
            String accessToken,
            String refreshToken,
            List<SignInResponseTokens> tokens,
            String email,
            UserRole role,
            String firstName,
            String lastName,
            List<SignInResponseTenants> tenants) {
        this();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokens = tokens;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tenants = tenants;
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

    public final List<SignInResponseTokens> getTokens() {
        return tokens;
    }

    public final SignInResponse setTokens(List<SignInResponseTokens> tokens) {
        this.tokens = tokens;
        return this;
    }

    public final String getEmail() {
        return email;
    }

    public final SignInResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public final UserRole getRole() {
        return role;
    }

    public final SignInResponse setRole(UserRole role) {
        this.role = role;
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

    public final List<SignInResponseTenants> getTenants() {
        return tenants;
    }

    public final SignInResponse setTenants(List<SignInResponseTenants> tenants) {
        this.tenants = tenants;
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
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = PRIME * result + ((this.role == null) ? 0 : this.role.hashCode());
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SignInResponse["
                + "this.tokens="
                + this.tokens
                + ", this.email="
                + this.email
                + ", this.role="
                + this.role
                + ", this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + ", this.tenants="
                + this.tenants
                + "]";
    }
}
