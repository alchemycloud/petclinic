package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

@Entity
@Table(name = "Vet")
public class Vet implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<VetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<User> USER = new PropertyPath<>("user");
    public static final PropertyPath<File> IMAGE = new PropertyPath<>("image");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @Column(name = "image")
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "path", column = @Column(name = "imagePath"))})
    private File image;

    public Vet() {}

    public VetId getId() {
        if (this.id == null) {
            return null;
        } else {
            return new VetId(this.id);
        }
    }

    public void setId(VetId id) {
        this.id = id.getValue();
    }

    public UserId getUserId() {
        if (user instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) user).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return new UserId((Long) lazyInitializer.getIdentifier());
            }
        }
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (this.getUser() != null) {
            this.getUser().removeVets(this);
        }
        this.user = user;
        this.getUser().addVets(this);
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vet)) {
            return false;
        }
        return Objects.equals(this.getId(), ((Vet) obj).getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + Vet.class.hashCode();

        if (this.getId() != null) {
            return PRIME * result + this.getId().hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Vet[" + "this.id=" + this.id + ", this.userId=" + this.getUserId() + ", this.image=" + this.image + "]";
    }
}
