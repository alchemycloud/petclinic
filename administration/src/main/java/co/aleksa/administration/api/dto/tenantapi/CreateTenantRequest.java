package co.aleksa.administration.api.dto.tenantapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateTenantRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> IDENTIFIER = new PropertyPath<>("identifier");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");

    @NotNull
    @Size(max = 255)
    private String identifier;

    @NotNull
    @Size(max = 255)
    private String name;

    private CreateTenantRequest() {}

    public CreateTenantRequest(String identifier, String name) {
        this();
        this.identifier = identifier;
        this.name = name;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final CreateTenantRequest setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final CreateTenantRequest setName(String name) {
        this.name = name;
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
        if (CreateTenantRequest.class != obj.getClass()) {
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
        return result;
    }

    @Override
    public String toString() {
        return "CreateTenantRequest[" + "this.identifier=" + this.identifier + ", this.name=" + this.name + "]";
    }
}
