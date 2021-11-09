package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.DataVersionId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DataVersion")
public class DataVersion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<DataVersionId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Integer> MAJOR = new PropertyPath<>("major");
    public static final PropertyPath<Integer> MINOR = new PropertyPath<>("minor");
    public static final PropertyPath<Integer> REVISION = new PropertyPath<>("revision");
    public static final PropertyPath<Integer> NUMBER = new PropertyPath<>("number");
    public static final PropertyPath<ZonedDateTime> TIME = new PropertyPath<>("time");
    public static final PropertyPath<Boolean> LOCK = new PropertyPath<>("lock");
    public static final PropertyPath<ZonedDateTime> LOCK_TIME = new PropertyPath<>("lockTime");

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
    @Column(name = "time")
    private ZonedDateTime time;

    @NotNull
    @Column(name = "`lock`")
    private Boolean lock;

    @NotNull
    @Column(name = "lockTime")
    private ZonedDateTime lockTime;

    public DataVersion() {}

    public DataVersionId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new DataVersionId(this.id);
        }
    }

    public void setId(DataVersionId id) {
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

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public ZonedDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(ZonedDateTime lockTime) {
        this.lockTime = lockTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DataVersion)) {
            return false;
        }
        return Objects.equals(this.getId(), ((DataVersion) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + DataVersion.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "DataVersion["
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
                + ", this.time="
                + this.time
                + ", this.lock="
                + this.lock
                + ", this.lockTime="
                + this.lockTime
                + "]";
    }
}
