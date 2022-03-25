package co.aleksa.administration.api.dto.tenantapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.id.TenantId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchTenantsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<TenantId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<String> IDENTIFIER = new PropertyPath<>("identifier");

    @NotNull private TenantId id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String identifier;

    private SearchTenantsResponse() {}

    public SearchTenantsResponse(TenantId id, String name, String identifier) {
        this();
        this.id = id;
        this.name = name;
        this.identifier = identifier;
    }

    public final TenantId getId() {
        return id;
    }

    public final SearchTenantsResponse setId(TenantId id) {
        this.id = id;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final SearchTenantsResponse setName(String name) {
        this.name = name;
        return this;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final SearchTenantsResponse setIdentifier(String identifier) {
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
        if (SearchTenantsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SearchTenantsResponse[" + "this.id=" + this.id + ", this.name=" + this.name + ", this.identifier=" + this.identifier + "]";
    }
}
