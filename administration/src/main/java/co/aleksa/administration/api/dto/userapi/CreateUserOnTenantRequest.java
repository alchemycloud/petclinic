package co.aleksa.administration.api.dto.userapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.enumeration.UserRole;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserOnTenantRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> TENANT = new PropertyPath<>("tenant");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<UserRole> ROLE = new PropertyPath<>("role");

    @NotNull
    @Size(max = 255)
    private String tenant;

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    @NotNull private UserRole role;

    private CreateUserOnTenantRequest() {}

    public CreateUserOnTenantRequest(String tenant, String email, UserRole role) {
        this();
        this.tenant = tenant;
        this.email = email;
        this.role = role;
    }

    public final String getTenant() {
        return tenant;
    }

    public final CreateUserOnTenantRequest setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public final String getEmail() {
        return email;
    }

    public final CreateUserOnTenantRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public final UserRole getRole() {
        return role;
    }

    public final CreateUserOnTenantRequest setRole(UserRole role) {
        this.role = role;
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
        if (CreateUserOnTenantRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.tenant == null) ? 0 : this.tenant.hashCode());
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = PRIME * result + ((this.role == null) ? 0 : this.role.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CreateUserOnTenantRequest[" + "this.tenant=" + this.tenant + ", this.email=" + this.email + ", this.role=" + this.role + "]";
    }
}
