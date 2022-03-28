package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.audit.AuditType;
import co.aleksa.backend.audit.changedtos.ValueDeltaDto;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.UserHistoryId;
import co.aleksa.backend.model.id.UserId;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<UserId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<ZonedDateTime> BIRTHDAY = new PropertyPath<>("birthday");
    public static final PropertyPath<Boolean> ACTIVE = new PropertyPath<>("active");
    public static final PropertyPath<UserRole> ROLE = new PropertyPath<>("role");
    public static final PropertyPath<Optional<UserHistory>> LAST_HISTORY = new PropertyPath<>("lastHistory");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = ".+\\@.+")
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @NotNull
    @Column(name = "active")
    private Boolean active;

    @NotNull
    @OrderBy
    @OneToMany(mappedBy = "Vet")
    private final Set<Vet> vets = new LinkedHashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @NotNull
    @OrderBy
    @OneToMany(mappedBy = "reference")
    private final Set<UserHistory> histories = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastHistoryId")
    private UserHistory lastHistory;

    public User() {}

    public UserId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new UserId(this.id);
        }
    }

    public void setId(UserId id) {
        this.id = id.getValue();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(FIRST_NAME), Optional.of(firstName));
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(LAST_NAME), Optional.of(lastName));
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(EMAIL), Optional.of(email));
        this.email = email;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(BIRTHDAY), Optional.of(birthday));
        this.birthday = birthday;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(ACTIVE), Optional.of(active));
        this.active = active;
    }

    public Set<Vet> getVets() {
        return Collections.unmodifiableSet(vets);
    }

    public final void removeVets(Vet item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final User instance = (User) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.vets)) {
            final Set<Vet> restOfSet = new LinkedHashSet<>(instance.vets);
            restOfSet.remove(item);
            instance.vets.clear();
            instance.vets.addAll(restOfSet);
        }
    }

    public final void addVets(Vet item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final User instance = (User) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.vets)) {
            final Set<Vet> restOfSet = new LinkedHashSet<>(instance.vets);
            restOfSet.add(item);
            instance.vets.clear();
            instance.vets.addAll(restOfSet);
        }
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        AppThreadLocals.getAuditContext().register(this, AuditType.UPDATE, Optional.of(ROLE), Optional.of(role));
        this.role = role;
    }

    public Set<UserHistory> getHistories() {
        return Collections.unmodifiableSet(histories);
    }

    public final void removeHistories(UserHistory item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final User instance = (User) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.histories)) {
            final Set<UserHistory> restOfSet = new LinkedHashSet<>(instance.histories);
            restOfSet.remove(item);
            instance.histories.clear();
            instance.histories.addAll(restOfSet);
        }
    }

    public final void addHistories(UserHistory item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final User instance = (User) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.histories)) {
            final Set<UserHistory> restOfSet = new LinkedHashSet<>(instance.histories);
            restOfSet.add(item);
            instance.histories.clear();
            instance.histories.addAll(restOfSet);
        }
    }

    public Optional<UserHistoryId> getLastHistoryId() {
        if (lastHistory instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) lastHistory).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return Optional.of(new UserHistoryId((Long) lazyInitializer.getIdentifier()));
            }
        }
        return Optional.ofNullable(lastHistory).map(UserHistory::getId);
    }

    public Optional<UserHistory> getLastHistory() {
        return Optional.ofNullable(lastHistory);
    }

    public void setLastHistory(Optional<UserHistory> lastHistory) {
        this.lastHistory = lastHistory.orElse(null);
    }

    public Map<PropertyPath, ValueDeltaDto> auditMapper() {
        final Map<PropertyPath, ValueDeltaDto> fieldValues = new HashMap<>();
        fieldValues.put(FIRST_NAME, new ValueDeltaDto(this.firstName, this.firstName));
        fieldValues.put(LAST_NAME, new ValueDeltaDto(this.lastName, this.lastName));
        fieldValues.put(EMAIL, new ValueDeltaDto(this.email, this.email));
        fieldValues.put(BIRTHDAY, new ValueDeltaDto(this.birthday, this.birthday));
        fieldValues.put(ACTIVE, new ValueDeltaDto(this.active, this.active));
        fieldValues.put(ROLE, new ValueDeltaDto(this.role, this.role));
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
        if (!(obj instanceof User)) {
            return false;
        }
        return Objects.equals(this.getId(), ((User) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + User.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "User["
                + "this.id="
                + this.id
                + ", this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + ", this.email="
                + this.email
                + ", this.birthday="
                + this.birthday
                + ", this.active="
                + this.active
                + ", this.role="
                + this.role
                + "]";
    }
}
