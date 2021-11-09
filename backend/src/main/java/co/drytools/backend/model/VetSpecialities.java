package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.model.id.VetSpecialitiesId;
import co.drytools.backend.model.id.VetSpecialityId;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "VetSpecialities")
public class VetSpecialities implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<VetSpecialitiesId> ID = new PropertyPath<>("id");
    public static final PropertyPath<Vet> VET = new PropertyPath<>("vet");
    public static final PropertyPath<VetSpeciality> SPECIALITY = new PropertyPath<>("speciality");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vetId")
    private Vet vet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialityId")
    private VetSpeciality speciality;

    public VetSpecialities() {}

    public VetSpecialitiesId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new VetSpecialitiesId(this.id);
        }
    }

    public void setId(VetSpecialitiesId id) {
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

    public VetSpecialityId getSpecialityId() {
        if (speciality instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) speciality).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new VetSpecialityId((Long) lazyInitializer.getIdentifier());
            }
        }
        return speciality.getId();
    }

    public VetSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(VetSpeciality speciality) {
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VetSpecialities)) {
            return false;
        }
        return Objects.equals(this.getId(), ((VetSpecialities) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + VetSpecialities.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "VetSpecialities[" + "this.id=" + this.id + ", this.vetId=" + this.getVetId() + ", this.specialityId=" + this.getSpecialityId() + "]";
    }
}
