package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
import javax.validation.constraints.Size;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "Pet")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Owner> OWNER = new PropertyPath<>("owner");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<ZonedDateTime> BIRTHDATE = new PropertyPath<>("birthdate");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<Boolean> VACCINATED = new PropertyPath<>("vaccinated");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private Owner owner;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "birthdate")
    private ZonedDateTime birthdate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "petType")
    private PetType petType;

    @NotNull
    @Column(name = "vaccinated")
    private Boolean vaccinated;

    public Pet() {}

    public PetId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new PetId(this.id);
        }
    }

    public void setId(PetId id) {
        this.id = id.getValue();
    }

    public OwnerId getOwnerId() {
        if (owner instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) owner).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new OwnerId((Long) lazyInitializer.getIdentifier());
            }
        }
        return owner.getId();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        if (this.getOwner() != null) {
            this.getOwner().removePets(this);
        }
        this.owner = owner;
        this.getOwner().addPets(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pet)) {
            return false;
        }
        return Objects.equals(this.getId(), ((Pet) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + Pet.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Pet["
                + "this.id="
                + this.id
                + ", this.ownerId="
                + this.getOwnerId()
                + ", this.name="
                + this.name
                + ", this.birthdate="
                + this.birthdate
                + ", this.petType="
                + this.petType
                + ", this.vaccinated="
                + this.vaccinated
                + "]";
    }
}
