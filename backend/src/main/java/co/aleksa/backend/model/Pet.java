package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.audit.AuditType;
import co.aleksa.backend.audit.changedtos.ValueDeltaDto;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.PetHistoryId;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.util.AppThreadLocals;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "Pet")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Owner> OWNER = new PropertyPath<>("owner");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<ZonedDateTime> BIRTHDAY = new PropertyPath<>("birthday");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<Boolean> VACCINATED = new PropertyPath<>("vaccinated");
    public static final PropertyPath<Optional<PetHistory>> LAST_HISTORY = new PropertyPath<>("lastHistory");

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
    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "petType")
    private PetType petType;

    @NotNull
    @Column(name = "vaccinated")
    private Boolean vaccinated;

    @NotNull
    @OrderBy
    @OneToMany(mappedBy = "reference")
    private final Set<PetHistory> histories = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastHistoryId")
    private PetHistory lastHistory;

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
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(OWNER), Optional.of(owner));
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
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(NAME), Optional.of(name));
        this.name = name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(BIRTHDAY), Optional.of(birthday));
        this.birthday = birthday;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(PET_TYPE), Optional.of(petType));
        this.petType = petType;
    }

    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(VACCINATED), Optional.of(vaccinated));
        this.vaccinated = vaccinated;
    }

    public Set<PetHistory> getHistories() {
        return Collections.unmodifiableSet(histories);
    }

    public final void removeHistories(PetHistory item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Pet instance = (Pet) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.histories)) {
            final Set<PetHistory> restOfSet = new LinkedHashSet<>(instance.histories);
            restOfSet.remove(item);
            instance.histories.clear();
            instance.histories.addAll(restOfSet);
        }
    }

    public final void addHistories(PetHistory item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final Pet instance = (Pet) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.histories)) {
            final Set<PetHistory> restOfSet = new LinkedHashSet<>(instance.histories);
            restOfSet.add(item);
            instance.histories.clear();
            instance.histories.addAll(restOfSet);
        }
    }

    public Optional<PetHistoryId> getLastHistoryId() {
        if (lastHistory instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) lastHistory).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return Optional.of(new PetHistoryId((Long) lazyInitializer.getIdentifier()));
            }
        }
        return Optional.ofNullable(lastHistory).map(PetHistory::getId);
    }

    public Optional<PetHistory> getLastHistory() {
        return Optional.ofNullable(lastHistory);
    }

    public void setLastHistory(Optional<PetHistory> lastHistory) {
        this.lastHistory = lastHistory.orElse(null);
    }

    public Map<PropertyPath, ValueDeltaDto> auditMapper() {
        final Map<PropertyPath, ValueDeltaDto> fieldValues = new HashMap<>();
        fieldValues.put(OWNER, new ValueDeltaDto(this.owner, this.owner));
        fieldValues.put(NAME, new ValueDeltaDto(this.name, this.name));
        fieldValues.put(BIRTHDAY, new ValueDeltaDto(this.birthday, this.birthday));
        fieldValues.put(PET_TYPE, new ValueDeltaDto(this.petType, this.petType));
        fieldValues.put(VACCINATED, new ValueDeltaDto(this.vaccinated, this.vaccinated));
        return fieldValues;
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
                + ", this.birthday="
                + this.birthday
                + ", this.petType="
                + this.petType
                + ", this.vaccinated="
                + this.vaccinated
                + "]";
    }
}
