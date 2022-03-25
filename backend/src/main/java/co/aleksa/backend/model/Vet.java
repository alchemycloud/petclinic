package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "Vet")
public class Vet implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<VetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<User> USER = new PropertyPath<>("user");
    public static final PropertyPath<File> IMAGE = new PropertyPath<>("image");
    public static final PropertyPath<User> USER = new PropertyPath<>("user");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "VetVetSpecialty",
            joinColumns = {@JoinColumn(name = "vetId")},
            inverseJoinColumns = {@JoinColumn(name = "vetSpecialtyId")})
    private final Set<VetSpecialty> specialties = new LinkedHashSet<>();

    @NotNull
    @Column(name = "image")
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "path", column = @Column(name = "imagePath"))})
    private File image;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Vet() {}

    public VetId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new VetId(this.id);
        }
    }

    public void setId(VetId id) {
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
        this.user = user;
    }

    public Set<VetSpecialty> getSpecialties() {
        return Collections.unmodifiableSet(specialties);
    }

    public final void removeSpecialties(VetSpecialty item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Vet instance = (Vet) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.specialties)) {
            final Set<VetSpecialty> restOfSet = new LinkedHashSet<>(instance.specialties);
            restOfSet.remove(item);
            instance.specialties.clear();
            instance.specialties.addAll(restOfSet);
        }
    }

    public final void addSpecialties(VetSpecialty item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Vet instance = (Vet) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.specialties)) {
            final Set<VetSpecialty> restOfSet = new LinkedHashSet<>(instance.specialties);
            restOfSet.add(item);
            instance.specialties.clear();
            instance.specialties.addAll(restOfSet);
        }
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
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
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vet)) {
            return false;
        }
        return Objects.equals(this.getId(), ((Vet) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + Vet.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Vet[" + "this.id=" + this.id + ", this.userId=" + this.getUserId() + ", this.image=" + this.image + ", this.userId=" + this.getUserId() + "]";
    }
}
