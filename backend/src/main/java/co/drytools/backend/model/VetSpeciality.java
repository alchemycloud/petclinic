package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.VetSpecialityId;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "VetSpeciality")
public class VetSpeciality implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<VetSpecialityId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<String> DESCRIPTION = new PropertyPath<>("description");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "description")
    private String description;

    public VetSpeciality() {}

    public VetSpecialityId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new VetSpecialityId(this.id);
        }
    }

    public void setId(VetSpecialityId id) {
        this.id = id.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VetSpeciality)) {
            return false;
        }
        return Objects.equals(this.getId(), ((VetSpeciality) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + VetSpeciality.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "VetSpeciality[" + "this.id=" + this.id + ", this.name=" + this.name + ", this.description=" + this.description + "]";
    }
}
