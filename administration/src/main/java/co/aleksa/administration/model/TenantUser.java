package co.aleksa.administration.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.administration.model.enumeration.TenantAccessLevel;
import co.aleksa.administration.model.id.TenantId;
import co.aleksa.administration.model.id.TenantUserId;
import co.aleksa.administration.model.id.UserId;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "TenantUser")
public class TenantUser implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<TenantUserId> ID = new PropertyPath<>("id");
    public static final PropertyPath<User> USER = new PropertyPath<>("user");
    public static final PropertyPath<Tenant> TENANT = new PropertyPath<>("tenant");
    public static final PropertyPath<TenantAccessLevel> ACCESS_LEVEL = new PropertyPath<>("accessLevel");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenantId")
    private Tenant tenant;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "accessLevel")
    private TenantAccessLevel accessLevel;

    public TenantUser() {}

    public TenantUserId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new TenantUserId(this.id);
        }
    }

    public void setId(TenantUserId id) {
        this.id = id.getValue();
    }

    public UserId getUserId() {
        if (user instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) user).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new UserId((Long) lazyInitializer.getIdentifier());
            }
        }
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (this.getUser() != null) {
            this.getUser().removeUserTenants(this);
        }
        this.user = user;
        this.getUser().addUserTenants(this);
    }

    public TenantId getTenantId() {
        if (tenant instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) tenant).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new TenantId((Long) lazyInitializer.getIdentifier());
            }
        }
        return tenant.getId();
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        if (this.getTenant() != null) {
            this.getTenant().removeTenantUsers(this);
        }
        this.tenant = tenant;
        this.getTenant().addTenantUsers(this);
    }

    public TenantAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TenantAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TenantUser)) {
            return false;
        }
        return Objects.equals(this.getId(), ((TenantUser) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + TenantUser.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "TenantUser["
                + "this.id="
                + this.id
                + ", this.userId="
                + this.getUserId()
                + ", this.tenantId="
                + this.getTenantId()
                + ", this.accessLevel="
                + this.accessLevel
                + "]";
    }
}
