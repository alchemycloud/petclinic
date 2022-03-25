package co.aleksa.administration.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.id.TenantId;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.Hibernate;

@Entity
@Table(name = "Tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<TenantId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<String> IDENTIFIER = new PropertyPath<>("identifier");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "identifier")
    private String identifier;

    @NotNull
    @OrderBy
    @OneToMany(mappedBy = "tenant")
    private final Set<TenantUser> tenantUsers = new LinkedHashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Set<TenantUser> getTenantUsers() {
        return Collections.unmodifiableSet(tenantUsers);
    }

    public final void removeTenantUsers(TenantUser item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Tenant instance = (Tenant) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.tenantUsers)) {
            final Set<TenantUser> restOfSet = new LinkedHashSet<>(instance.tenantUsers);
            restOfSet.remove(item);
            instance.tenantUsers.clear();
            instance.tenantUsers.addAll(restOfSet);
        }
    }

    public final void addTenantUsers(TenantUser item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Tenant instance = (Tenant) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.tenantUsers)) {
            final Set<TenantUser> restOfSet = new LinkedHashSet<>(instance.tenantUsers);
            restOfSet.add(item);
            instance.tenantUsers.clear();
            instance.tenantUsers.addAll(restOfSet);
        }
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
        return "Tenant[" + "this.id=" + this.id + ", this.name=" + this.name + ", this.identifier=" + this.identifier + "]";
    }
}
