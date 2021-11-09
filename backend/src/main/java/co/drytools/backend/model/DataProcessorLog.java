package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.DataProcessorLogId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DataProcessorLog")
public class DataProcessorLog implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<DataProcessorLogId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Integer> MAJOR = new PropertyPath<>("major");
    public static final PropertyPath<Integer> MINOR = new PropertyPath<>("minor");
    public static final PropertyPath<Integer> REVISION = new PropertyPath<>("revision");
    public static final PropertyPath<Integer> NUMBER = new PropertyPath<>("number");
    public static final PropertyPath<String> DESCRIPTION = new PropertyPath<>("description");
    public static final PropertyPath<ZonedDateTime> STARTED = new PropertyPath<>("started");
    public static final PropertyPath<Optional<ZonedDateTime>> COMPLETED = new PropertyPath<>("completed");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    @Column(name = "major")
    private Integer major;

    @NotNull
    @Min(0)
    @Column(name = "minor")
    private Integer minor;

    @NotNull
    @Min(0)
    @Column(name = "revision")
    private Integer revision;

    @NotNull
    @Min(0)
    @Column(name = "number")
    private Integer number;

    @NotNull
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "started")
    private ZonedDateTime started;

    @Column(name = "completed")
    private ZonedDateTime completed;

    public DataProcessorLog() {}

    public DataProcessorLogId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new DataProcessorLogId(this.id);
        }
    }

    public void setId(DataProcessorLogId id) {
        this.id = id.getValue();
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStarted() {
        return started;
    }

    public void setStarted(ZonedDateTime started) {
        this.started = started;
    }

    public Optional<ZonedDateTime> getCompleted() {
        return Optional.ofNullable(completed);
    }

    public void setCompleted(Optional<ZonedDateTime> completed) {
        this.completed = completed.orElse(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DataProcessorLog)) {
            return false;
        }
        return Objects.equals(this.getId(), ((DataProcessorLog) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + DataProcessorLog.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "DataProcessorLog["
                + "this.id="
                + this.id
                + ", this.major="
                + this.major
                + ", this.minor="
                + this.minor
                + ", this.revision="
                + this.revision
                + ", this.number="
                + this.number
                + ", this.description="
                + this.description
                + ", this.started="
                + this.started
                + ", this.completed="
                + this.completed
                + "]";
    }
}
