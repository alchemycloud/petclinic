package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetHistoryId;
import co.aleksa.backend.model.id.PetId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
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
@Table(name = "PetHistory")
public class PetHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<PetHistoryId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Optional<String>> CORRELATION_ID = new PropertyPath<>("correlationId");
    public static final PropertyPath<Long> ENTITY_ID = new PropertyPath<>("entityId");
    public static final PropertyPath<Optional<Pet>> REFERENCE = new PropertyPath<>("reference");
    public static final PropertyPath<ZonedDateTime> CHANGE_TIME = new PropertyPath<>("changeTime");
    public static final PropertyPath<Optional<PetHistory>> PREVIOUS = new PropertyPath<>("previous");
    public static final PropertyPath<Optional<Long>> OWNER_ID = new PropertyPath<>("ownerId");
    public static final PropertyPath<Optional<String>> NAME = new PropertyPath<>("name");
    public static final PropertyPath<Optional<ZonedDateTime>> BIRTHDAY = new PropertyPath<>("birthday");
    public static final PropertyPath<Optional<PetType>> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<Optional<Boolean>> VACCINATED = new PropertyPath<>("vaccinated");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "correlationId")
    private String correlationId;

    @NotNull
    @Column(name = "entityId")
    private Long entityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenceId")
    private Pet reference;

    @NotNull
    @Column(name = "changeTime")
    private ZonedDateTime changeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previousId")
    private PetHistory previous;

    @Column(name = "ownerId")
    private Long ownerId;

    @Size(min = 2, max = 40)
    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "petType")
    private PetType petType;

    @Column(name = "vaccinated")
    private Boolean vaccinated;

    public PetHistory() {}

    public PetHistoryId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new PetHistoryId(this.id);
        }
    }

    public void setId(PetHistoryId id) {
        this.id = id.getValue();
    }

    public Optional<String> getCorrelationId() {
        return Optional.ofNullable(correlationId);
    }

    public void setCorrelationId(Optional<String> correlationId) {
        this.correlationId = correlationId.orElse(null);
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Optional<PetId> getReferenceId() {
        if (reference instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) reference).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return Optional.of(new PetId((Long) lazyInitializer.getIdentifier()));
            }
        }
        return Optional.ofNullable(reference).map(Pet::getId);
    }

    public Optional<Pet> getReference() {
        return Optional.ofNullable(reference);
    }

    public void setReference(Optional<Pet> reference) {
        if (this.getReference().isPresent()) {
            this.getReference().get().removeHistories(this);
        }
        this.reference = reference.orElse(null);
        if (this.getReference().isPresent()) {
            this.getReference().get().addHistories(this);
        }
    }

    public ZonedDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(ZonedDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public Optional<PetHistoryId> getPreviousId() {
        if (previous instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) previous).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return Optional.of(new PetHistoryId((Long) lazyInitializer.getIdentifier()));
            }
        }
        return Optional.ofNullable(previous).map(PetHistory::getId);
    }

    public Optional<PetHistory> getPrevious() {
        return Optional.ofNullable(previous);
    }

    public void setPrevious(Optional<PetHistory> previous) {
        this.previous = previous.orElse(null);
    }

    public Optional<Long> getOwnerId() {
        return Optional.ofNullable(ownerId);
    }

    public void setOwnerId(Optional<Long> ownerId) {
        this.ownerId = ownerId.orElse(null);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(Optional<String> name) {
        this.name = name.orElse(null);
    }

    public Optional<ZonedDateTime> getBirthday() {
        return Optional.ofNullable(birthday);
    }

    public void setBirthday(Optional<ZonedDateTime> birthday) {
        this.birthday = birthday.orElse(null);
    }

    public Optional<PetType> getPetType() {
        return Optional.ofNullable(petType);
    }

    public void setPetType(Optional<PetType> petType) {
        this.petType = petType.orElse(null);
    }

    public Optional<Boolean> getVaccinated() {
        return Optional.ofNullable(vaccinated);
    }

    public void setVaccinated(Optional<Boolean> vaccinated) {
        this.vaccinated = vaccinated.orElse(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PetHistory)) {
            return false;
        }
        return Objects.equals(this.getId(), ((PetHistory) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + PetHistory.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "PetHistory["
                + "this.id="
                + this.id
                + ", this.correlationId="
                + this.correlationId
                + ", this.entityId="
                + this.entityId
                + ", this.referenceId="
                + this.getReferenceId()
                + ", this.changeTime="
                + this.changeTime
                + ", this.previousId="
                + this.getPreviousId()
                + ", this.ownerId="
                + this.ownerId
                + ", this.name="
                + this.name
                + ", this.birthday="
                + this.birthday
                + ", this.petType="
                + this.petType
                + ", this.vaccinated="
                + this.vaccinated
                + "]";
    }
}
