package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VisitId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Visit")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<VisitId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Vet> VET = new PropertyPath<>("vet");
    public static final PropertyPath<Pet> PET = new PropertyPath<>("pet");
    public static final PropertyPath<Integer> VISIT_NUMBER = new PropertyPath<>("visitNumber");
    public static final PropertyPath<ZonedDateTime> TIMESTAMP = new PropertyPath<>("timestamp");
    public static final PropertyPath<Optional<BigDecimal>> PET_WEIGHT = new PropertyPath<>("petWeight");
    public static final PropertyPath<Optional<String>> DESCRIPTION = new PropertyPath<>("description");
    public static final PropertyPath<Boolean> SCHEDULED = new PropertyPath<>("scheduled");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vetId")
    private Vet vet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petId")
    private Pet pet;

    @NotNull
    @Column(name = "visitNumber")
    private Integer visitNumber;

    @NotNull
    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @Column(name = "petWeight")
    private BigDecimal petWeight;

    @Size(max = 1024)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "scheduled")
    private Boolean scheduled;

    public Visit() {}

    public VisitId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new VisitId(this.id);
        }
    }

    public void setId(VisitId id) {
        this.id = id.getValue();
    }

    public VetId getVetId() {
        if (vet instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) vet).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new VetId((Long) lazyInitializer.getIdentifier());
            }
        }
        return vet.getId();
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public PetId getPetId() {
        if (pet instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) pet).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new PetId((Long) lazyInitializer.getIdentifier());
            }
        }
        return pet.getId();
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Integer getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Optional<BigDecimal> getPetWeight() {
        return Optional.ofNullable(petWeight);
    }

    public void setPetWeight(Optional<BigDecimal> petWeight) {
        this.petWeight = petWeight.orElse(null);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Optional<String> description) {
        this.description = description.orElse(null);
    }

    public Boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Visit)) {
            return false;
        }
        return Objects.equals(this.getId(), ((Visit) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + Visit.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Visit["
                + "this.id="
                + this.id
                + ", this.vetId="
                + this.getVetId()
                + ", this.petId="
                + this.getPetId()
                + ", this.visitNumber="
                + this.visitNumber
                + ", this.timestamp="
                + this.timestamp
                + ", this.petWeight="
                + this.petWeight
                + ", this.description="
                + this.description
                + ", this.scheduled="
                + this.scheduled
                + "]";
    }
}
