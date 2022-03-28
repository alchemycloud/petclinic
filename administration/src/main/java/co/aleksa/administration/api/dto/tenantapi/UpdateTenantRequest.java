package co.aleksa.administration.api.dto.tenantapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateTenantRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> NAME = new PropertyPath<>("name");

    @NotNull
    @Size(max = 255)
    private String name;

    private UpdateTenantRequest() {}

    public UpdateTenantRequest(String name) {
        this();
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final UpdateTenantRequest setName(String name) {
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
        if (UpdateTenantRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UpdateTenantRequest[" + "this.name=" + this.name + "]";
    }
}
