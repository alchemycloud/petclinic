package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.UserHistoryId;
import co.aleksa.backend.model.id.UserId;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "UserHistory")
public class UserHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<UserHistoryId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Optional<String>> CORRELATION_ID = new PropertyPath<>("correlationId");
    public static final PropertyPath<Long> ENTITY_ID = new PropertyPath<>("entityId");
    public static final PropertyPath<Optional<User>> REFERENCE = new PropertyPath<>("reference");
    public static final PropertyPath<ZonedDateTime> CHANGE_TIME = new PropertyPath<>("changeTime");
    public static final PropertyPath<Optional<UserHistory>> PREVIOUS = new PropertyPath<>("previous");
    public static final PropertyPath<Optional<String>> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<Optional<String>> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<Optional<String>> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<Optional<ZonedDateTime>> BIRTHDAY = new PropertyPath<>("birthday");
    public static final PropertyPath<Optional<Boolean>> ACTIVE = new PropertyPath<>("active");
    public static final PropertyPath<Optional<UserRole>> ROLE = new PropertyPath<>("role");

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
    private User reference;

    @NotNull
    @Column(name = "changeTime")
    private ZonedDateTime changeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previousId")
    private UserHistory previous;

    @Size(min = 1, max = 40)
    @Column(name = "firstName")
    private String firstName;

    @Size(min = 1, max = 60)
    @Column(name = "lastName")
    private String lastName;

    @Size(max = 255)
    @Pattern(regexp = ".+\\@.+")
    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    public UserHistory() {}

    public UserHistoryId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new UserHistoryId(this.id);
        }
    }

    public void setId(UserHistoryId id) {
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

    public Optional<UserId> getReferenceId() {
        if (reference instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) reference).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return Optional.of(new UserId((Long) lazyInitializer.getIdentifier()));
            }
        }
        return Optional.ofNullable(reference).map(User::getId);
    }

    public Optional<User> getReference() {
        return Optional.ofNullable(reference);
    }

    public void setReference(Optional<User> reference) {
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

    public Optional<UserHistoryId> getPreviousId() {
        if (previous instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) previous).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return Optional.of(new UserHistoryId((Long) lazyInitializer.getIdentifier()));
            }
        }
        return Optional.ofNullable(previous).map(UserHistory::getId);
    }

    public Optional<UserHistory> getPrevious() {
        return Optional.ofNullable(previous);
    }

    public void setPrevious(Optional<UserHistory> previous) {
        this.previous = previous.orElse(null);
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName.orElse(null);
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName.orElse(null);
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(Optional<String> email) {
        this.email = email.orElse(null);
    }

    public Optional<ZonedDateTime> getBirthday() {
        return Optional.ofNullable(birthday);
    }

    public void setBirthday(Optional<ZonedDateTime> birthday) {
        this.birthday = birthday.orElse(null);
    }

    public Optional<Boolean> getActive() {
        return Optional.ofNullable(active);
    }

    public void setActive(Optional<Boolean> active) {
        this.active = active.orElse(null);
    }

    public Optional<UserRole> getRole() {
        return Optional.ofNullable(role);
    }

    public void setRole(Optional<UserRole> role) {
        this.role = role.orElse(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserHistory)) {
            return false;
        }
        return Objects.equals(this.getId(), ((UserHistory) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + UserHistory.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "UserHistory["
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
