package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "Owner")
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<User> USER = new PropertyPath<>("user");
    public static final PropertyPath<Optional<String>> ADDRESS = new PropertyPath<>("address");
    public static final PropertyPath<Optional<String>> CITY = new PropertyPath<>("city");
    public static final PropertyPath<Optional<String>> TELEPHONE = new PropertyPath<>("telephone");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;

    @Size(min = 5, max = 100)
    @Column(name = "address")
    private String address;

    @Size(min = 2, max = 50)
    @Column(name = "city")
    private String city;

    @Size(min = 5, max = 15)
    @Column(name = "telephone")
    private String telephone;

    @NotNull
    @OrderBy
    @OneToMany(mappedBy = "owner")
    private final Set<Pet> pets = new LinkedHashSet<>();

    public Owner() {}

    public OwnerId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new OwnerId(this.id);
        }
    }

    public void setId(OwnerId id) {
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

    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Optional<String> address) {
        this.address = address.orElse(null);
    }

    public Optional<String> getCity() {
        return Optional.ofNullable(city);
    }

    public void setCity(Optional<String> city) {
        this.city = city.orElse(null);
    }

    public Optional<String> getTelephone() {
        return Optional.ofNullable(telephone);
    }

    public void setTelephone(Optional<String> telephone) {
        this.telephone = telephone.orElse(null);
    }

    public Set<Pet> getPets() {
        return Collections.unmodifiableSet(pets);
    }

    public final void removePets(Pet item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Owner instance = (Owner) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.pets)) {
            final Set<Pet> restOfSet = new LinkedHashSet<>(instance.pets);
            restOfSet.remove(item);
            instance.pets.clear();
            instance.pets.addAll(restOfSet);
        }
    }

    public final void addPets(Pet item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Owner instance = (Owner) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.pets)) {
            final Set<Pet> restOfSet = new LinkedHashSet<>(instance.pets);
            restOfSet.add(item);
            instance.pets.clear();
            instance.pets.addAll(restOfSet);
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
        if (!(obj instanceof Owner)) {
            return false;
        }
        return Objects.equals(this.getId(), ((Owner) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + Owner.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Owner["
                + "this.id="
                + this.id
                + ", this.userId="
                + this.getUserId()
                + ", this.address="
                + this.address
                + ", this.city="
                + this.city
                + ", this.telephone="
                + this.telephone
                + "]";
    }
}
