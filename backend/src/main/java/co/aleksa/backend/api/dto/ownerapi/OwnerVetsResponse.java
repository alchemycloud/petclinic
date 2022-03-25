package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.UserId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OwnerVetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<String> ADDRESS = new PropertyPath<>("address");
    public static final PropertyPath<String> CITY = new PropertyPath<>("city");
    public static final PropertyPath<String> TELEPHONE = new PropertyPath<>("telephone");
    public static final PropertyPath<String> USER_FIRST_NAME = new PropertyPath<>("userFirstName");
    public static final PropertyPath<String> USER_LAST_NAME = new PropertyPath<>("userLastName");
    public static final PropertyPath<String> USER_EMAIL = new PropertyPath<>("userEmail");
    public static final PropertyPath<ZonedDateTime> USER_BIRTHDAY = new PropertyPath<>("userBirthday");
    public static final PropertyPath<Boolean> USER_ACTIVE = new PropertyPath<>("userActive");
    public static final PropertyPath<UserRole> USER_ROLE = new PropertyPath<>("userRole");

    @NotNull private OwnerId id;

    @NotNull private UserId userId;

    @Size(min = 5, max = 100)
    private String address;

    @Size(min = 2, max = 50)
    private String city;

    @Size(min = 5, max = 15)
    private String telephone;

    @NotNull
    @Size(min = 1, max = 40)
    private String userFirstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String userLastName;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = ".+\\@.+")
    private String userEmail;

    @NotNull private ZonedDateTime userBirthday;

    @NotNull private Boolean userActive;

    @NotNull private UserRole userRole;

    private OwnerVetsResponse() {}

    public OwnerVetsResponse(
            OwnerId id,
            UserId userId,
            String address,
            String city,
            String telephone,
            String userFirstName,
            String userLastName,
            String userEmail,
            ZonedDateTime userBirthday,
            Boolean userActive,
            UserRole userRole) {
        this();
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userBirthday = userBirthday;
        this.userActive = userActive;
        this.userRole = userRole;
    }

    public final OwnerId getId() {
        return id;
    }

    public final OwnerVetsResponse setId(OwnerId id) {
        this.id = id;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final OwnerVetsResponse setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final String getAddress() {
        return address;
    }

    public final OwnerVetsResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public final String getCity() {
        return city;
    }

    public final OwnerVetsResponse setCity(String city) {
        this.city = city;
        return this;
    }

    public final String getTelephone() {
        return telephone;
    }

    public final OwnerVetsResponse setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public final String getUserFirstName() {
        return userFirstName;
    }

    public final OwnerVetsResponse setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public final String getUserLastName() {
        return userLastName;
    }

    public final OwnerVetsResponse setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public final String getUserEmail() {
        return userEmail;
    }

    public final OwnerVetsResponse setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public final ZonedDateTime getUserBirthday() {
        return userBirthday;
    }

    public final OwnerVetsResponse setUserBirthday(ZonedDateTime userBirthday) {
        this.userBirthday = userBirthday;
        return this;
    }

    public final Boolean getUserActive() {
        return userActive;
    }

    public final OwnerVetsResponse setUserActive(Boolean userActive) {
        this.userActive = userActive;
        return this;
    }

    public final UserRole getUserRole() {
        return userRole;
    }

    public final OwnerVetsResponse setUserRole(UserRole userRole) {
        this.userRole = userRole;
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
        if (OwnerVetsResponse.class != obj.getClass()) {
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
        result = PRIME * result + ((this.userFirstName == null) ? 0 : this.userFirstName.hashCode());
        result = PRIME * result + ((this.userLastName == null) ? 0 : this.userLastName.hashCode());
        result = PRIME * result + ((this.userEmail == null) ? 0 : this.userEmail.hashCode());
        result = PRIME * result + ((this.userBirthday == null) ? 0 : this.userBirthday.hashCode());
        result = PRIME * result + ((this.userActive == null) ? 0 : this.userActive.hashCode());
        result = PRIME * result + ((this.userRole == null) ? 0 : this.userRole.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "OwnerVetsResponse["
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
                + ", this.userFirstName="
                + this.userFirstName
                + ", this.userLastName="
                + this.userLastName
                + ", this.userEmail="
                + this.userEmail
                + ", this.userBirthday="
                + this.userBirthday
                + ", this.userActive="
                + this.userActive
                + ", this.userRole="
                + this.userRole
                + "]";
    }
}
