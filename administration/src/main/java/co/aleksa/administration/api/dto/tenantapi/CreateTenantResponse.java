package co.aleksa.administration.api.dto.tenantapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateTenantResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> IDENTIFIER = new PropertyPath<>("identifier");

    @NotNull
    @Size(max = 255)
    private String identifier;

    private CreateTenantResponse() {}

    public CreateTenantResponse(String identifier) {
        this();
        this.identifier = identifier;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final CreateTenantResponse setIdentifier(String identifier) {
        this.identifier = identifier;
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
        if (CreateTenantResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CreateTenantResponse[" + "this.identifier=" + this.identifier + "]";
    }
}
