package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.TenantId;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<TenantId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> IDENTIFIER = new PropertyPath<>("identifier");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "identifier")
    private String identifier;

    public Tenant() {}

    public TenantId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new TenantId(this.id);
        }
    }

    public void setId(TenantId id) {
        this.id = id.getValue();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Tenant)) {
            return false;
        }
        return Objects.equals(this.getId(), ((Tenant) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + Tenant.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Tenant[" + "this.id=" + this.id + ", this.identifier=" + this.identifier + "]";
    }
}
