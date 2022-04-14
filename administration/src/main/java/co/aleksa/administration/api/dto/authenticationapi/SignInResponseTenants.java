package co.aleksa.administration.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.enumeration.TenantAccessLevel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignInResponseTenants implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> IDENTIFIER = new PropertyPath<>("identifier");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<TenantAccessLevel> ACCESS_LEVEL = new PropertyPath<>("accessLevel");

    @NotNull
    @Size(max = 255)
    private String identifier;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull private TenantAccessLevel accessLevel;

    private SignInResponseTenants() {}

    public SignInResponseTenants(String identifier, String name, TenantAccessLevel accessLevel) {
        this();
        this.identifier = identifier;
        this.name = name;
        this.accessLevel = accessLevel;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final SignInResponseTenants setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final SignInResponseTenants setName(String name) {
        this.name = name;
        return this;
    }

    public final TenantAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public final SignInResponseTenants setAccessLevel(TenantAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
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
        if (SignInResponseTenants.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.accessLevel == null) ? 0 : this.accessLevel.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SignInResponseTenants[" + "this.identifier=" + this.identifier + ", this.name=" + this.name + ", this.accessLevel=" + this.accessLevel + "]";
    }
}
