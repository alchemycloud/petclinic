package co.aleksa.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.VetSpecialtyId;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.Hibernate;

@Entity
@Table(name = "VetSpecialty")
public class VetSpecialty implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<VetSpecialtyId> ID = new PropertyPath<>("id");
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

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "VetSpecialtyVet",
            joinColumns = {@JoinColumn(name = "vetSpecialtyId")},
            inverseJoinColumns = {@JoinColumn(name = "vetId")})
    private final Set<Vet> vets = new LinkedHashSet<>();

    public VetSpecialty() {}

    public VetSpecialtyId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new VetSpecialtyId(this.id);
        }
    }

    public void setId(VetSpecialtyId id) {
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

    public Set<Vet> getVets() {
        return Collections.unmodifiableSet(vets);
    }

    public final void removeVets(Vet item) {
        if (!Hibernate.isInitialized(this)) {
            return;
        }
        final VetSpecialty instance = (VetSpecialty) Hibernate.unproxy(this);
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
        final VetSpecialty instance = (VetSpecialty) Hibernate.unproxy(this);
        if (Hibernate.isInitialized(instance.vets)) {
            final Set<Vet> restOfSet = new LinkedHashSet<>(instance.vets);
            restOfSet.add(item);
            instance.vets.clear();
            instance.vets.addAll(restOfSet);
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
        if (!(obj instanceof VetSpecialty)) {
            return false;
        }
        return Objects.equals(this.getId(), ((VetSpecialty) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + VetSpecialty.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "VetSpecialty[" + "this.id=" + this.id + ", this.name=" + this.name + ", this.description=" + this.description + "]";
    }
}
