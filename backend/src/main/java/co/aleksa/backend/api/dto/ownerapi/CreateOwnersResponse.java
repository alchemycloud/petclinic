package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.UserId;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateOwnersResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<String> ADDRESS = new PropertyPath<>("address");
    public static final PropertyPath<String> CITY = new PropertyPath<>("city");
    public static final PropertyPath<String> TELEPHONE = new PropertyPath<>("telephone");

    @NotNull private OwnerId id;

    @NotNull private UserId userId;

    @Size(min = 5, max = 100)
    private String address;

    @Size(min = 2, max = 50)
    private String city;

    @Size(min = 5, max = 15)
    private String telephone;

    private CreateOwnersResponse() {}

    public CreateOwnersResponse(OwnerId id, UserId userId, String address, String city, String telephone) {
        this();
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public final OwnerId getId() {
        return id;
    }

    public final CreateOwnersResponse setId(OwnerId id) {
        this.id = id;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final CreateOwnersResponse setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final String getAddress() {
        return address;
    }

    public final CreateOwnersResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public final String getCity() {
        return city;
    }

    public final CreateOwnersResponse setCity(String city) {
        this.city = city;
        return this;
    }

    public final String getTelephone() {
        return telephone;
    }

    public final CreateOwnersResponse setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (CreateOwnersResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = PRIME * result + ((this.address == null) ? 0 : this.address.hashCode());
        result = PRIME * result + ((this.city == null) ? 0 : this.city.hashCode());
        result = PRIME * result + ((this.telephone == null) ? 0 : this.telephone.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CreateOwnersResponse["
                + "this.id="
                + this.id
                + ", this.userId="
                + this.userId
                + ", this.address="
                + this.address
                + ", this.city="
                + this.city
                + ", this.telephone="
                + this.telephone
                + "]";
    }
}
