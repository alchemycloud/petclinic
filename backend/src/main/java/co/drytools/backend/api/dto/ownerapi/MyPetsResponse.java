package co.drytools.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MyPetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> ID = new PropertyPath<>("id");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<String> ADDRESS = new PropertyPath<>("address");
    public static final PropertyPath<String> CITY = new PropertyPath<>("city");
    public static final PropertyPath<String> TELEPHONE = new PropertyPath<>("telephone");
    public static final PropertyPath<PetId> PET_ID = new PropertyPath<>("petId");
    public static final PropertyPath<OwnerId> PET_OWNER_ID = new PropertyPath<>("petOwnerId");
    public static final PropertyPath<String> PET_NAME = new PropertyPath<>("petName");
    public static final PropertyPath<ZonedDateTime> PET_BIRTHDATE = new PropertyPath<>("petBirthdate");
    public static final PropertyPath<PetType> PET_PET_TYPE = new PropertyPath<>("petPetType");
    public static final PropertyPath<Boolean> PET_VACCINATED = new PropertyPath<>("petVaccinated");
    public static final PropertyPath<String> USER_FIRST_NAME = new PropertyPath<>("userFirstName");
    public static final PropertyPath<String> USER_LAST_NAME = new PropertyPath<>("userLastName");
    public static final PropertyPath<ZonedDateTime> USER_BIRTHDATE = new PropertyPath<>("userBirthdate");
    public static final PropertyPath<Boolean> USER_ACTIVE = new PropertyPath<>("userActive");
    public static final PropertyPath<UserRole> USER_ROLE = new PropertyPath<>("userRole");
    public static final PropertyPath<String> USER_EMAIL = new PropertyPath<>("userEmail");
    public static final PropertyPath<String> USER_PASSWORD_HASH = new PropertyPath<>("userPasswordHash");
    public static final PropertyPath<String> USER_EMAIL_VERIFICATION_CODE = new PropertyPath<>("userEmailVerificationCode");
    public static final PropertyPath<ZonedDateTime> USER_EMAIL_VERIFICATION_CODE_TIMESTAMP = new PropertyPath<>("userEmailVerificationCodeTimestamp");
    public static final PropertyPath<Boolean> USER_EMAIL_VERIFIED = new PropertyPath<>("userEmailVerified");
    public static final PropertyPath<String> USER_RESET_PASSWORD_CODE = new PropertyPath<>("userResetPasswordCode");
    public static final PropertyPath<ZonedDateTime> USER_RESET_PASSWORD_CODE_TIMESTAMP = new PropertyPath<>("userResetPasswordCodeTimestamp");

    @NotNull private OwnerId id;

    @NotNull private UserId userId;

    @Size(min = 5, max = 100)
    private String address;

    @Size(min = 2, max = 50)
    private String city;

    @Size(min = 5, max = 15)
    private String telephone;

    @NotNull private PetId petId;

    @NotNull private OwnerId petOwnerId;

    @NotNull
    @Size(min = 2, max = 40)
    private String petName;

    @NotNull private ZonedDateTime petBirthdate;

    @NotNull private PetType petPetType;

    @NotNull private Boolean petVaccinated;

    @NotNull
    @Size(min = 1, max = 40)
    private String userFirstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String userLastName;

    @NotNull private ZonedDateTime userBirthdate;

    @NotNull private Boolean userActive;

    @NotNull private UserRole userRole;

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String userEmail;

    @NotNull
    @Size(min = 6, max = 128)
    private String userPasswordHash;

    @Size(min = 64, max = 64)
    private String userEmailVerificationCode;

    private ZonedDateTime userEmailVerificationCodeTimestamp;

    @NotNull private Boolean userEmailVerified;

    @Size(min = 64, max = 64)
    private String userResetPasswordCode;

    private ZonedDateTime userResetPasswordCodeTimestamp;

    private MyPetsResponse() {}

    public MyPetsResponse(
            OwnerId id,
            UserId userId,
            String address,
            String city,
            String telephone,
            PetId petId,
            OwnerId petOwnerId,
            String petName,
            ZonedDateTime petBirthdate,
            PetType petPetType,
            Boolean petVaccinated,
            String userFirstName,
            String userLastName,
            ZonedDateTime userBirthdate,
            Boolean userActive,
            UserRole userRole,
            String userEmail,
            String userPasswordHash,
            String userEmailVerificationCode,
            ZonedDateTime userEmailVerificationCodeTimestamp,
            Boolean userEmailVerified,
            String userResetPasswordCode,
            ZonedDateTime userResetPasswordCodeTimestamp) {
        this();
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.petId = petId;
        this.petOwnerId = petOwnerId;
        this.petName = petName;
        this.petBirthdate = petBirthdate;
        this.petPetType = petPetType;
        this.petVaccinated = petVaccinated;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userBirthdate = userBirthdate;
        this.userActive = userActive;
        this.userRole = userRole;
        this.userEmail = userEmail;
        this.userPasswordHash = userPasswordHash;
        this.userEmailVerificationCode = userEmailVerificationCode;
        this.userEmailVerificationCodeTimestamp = userEmailVerificationCodeTimestamp;
        this.userEmailVerified = userEmailVerified;
        this.userResetPasswordCode = userResetPasswordCode;
        this.userResetPasswordCodeTimestamp = userResetPasswordCodeTimestamp;
    }

    public final OwnerId getId() {
        return id;
    }

    public final MyPetsResponse setId(OwnerId id) {
        this.id = id;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final MyPetsResponse setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final String getAddress() {
        return address;
    }

    public final MyPetsResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public final String getCity() {
        return city;
    }

    public final MyPetsResponse setCity(String city) {
        this.city = city;
        return this;
    }

    public final String getTelephone() {
        return telephone;
    }

    public final MyPetsResponse setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public final PetId getPetId() {
        return petId;
    }

    public final MyPetsResponse setPetId(PetId petId) {
        this.petId = petId;
        return this;
    }

    public final OwnerId getPetOwnerId() {
        return petOwnerId;
    }

    public final MyPetsResponse setPetOwnerId(OwnerId petOwnerId) {
        this.petOwnerId = petOwnerId;
        return this;
    }

    public final String getPetName() {
        return petName;
    }

    public final MyPetsResponse setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public final ZonedDateTime getPetBirthdate() {
        return petBirthdate;
    }

    public final MyPetsResponse setPetBirthdate(ZonedDateTime petBirthdate) {
        this.petBirthdate = petBirthdate;
        return this;
    }

    public final PetType getPetPetType() {
        return petPetType;
    }

    public final MyPetsResponse setPetPetType(PetType petPetType) {
        this.petPetType = petPetType;
        return this;
    }

    public final Boolean getPetVaccinated() {
        return petVaccinated;
    }

    public final MyPetsResponse setPetVaccinated(Boolean petVaccinated) {
        this.petVaccinated = petVaccinated;
        return this;
    }

    public final String getUserFirstName() {
        return userFirstName;
    }

    public final MyPetsResponse setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public final String getUserLastName() {
        return userLastName;
    }

    public final MyPetsResponse setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public final ZonedDateTime getUserBirthdate() {
        return userBirthdate;
    }

    public final MyPetsResponse setUserBirthdate(ZonedDateTime userBirthdate) {
        this.userBirthdate = userBirthdate;
        return this;
    }

    public final Boolean getUserActive() {
        return userActive;
    }

    public final MyPetsResponse setUserActive(Boolean userActive) {
        this.userActive = userActive;
        return this;
    }

    public final UserRole getUserRole() {
        return userRole;
    }

    public final MyPetsResponse setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public final String getUserEmail() {
        return userEmail;
    }

    public final MyPetsResponse setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public final String getUserPasswordHash() {
        return userPasswordHash;
    }

    public final MyPetsResponse setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
        return this;
    }

    public final String getUserEmailVerificationCode() {
        return userEmailVerificationCode;
    }

    public final MyPetsResponse setUserEmailVerificationCode(String userEmailVerificationCode) {
        this.userEmailVerificationCode = userEmailVerificationCode;
        return this;
    }

    public final ZonedDateTime getUserEmailVerificationCodeTimestamp() {
        return userEmailVerificationCodeTimestamp;
    }

    public final MyPetsResponse setUserEmailVerificationCodeTimestamp(ZonedDateTime userEmailVerificationCodeTimestamp) {
        this.userEmailVerificationCodeTimestamp = userEmailVerificationCodeTimestamp;
        return this;
    }

    public final Boolean getUserEmailVerified() {
        return userEmailVerified;
    }

    public final MyPetsResponse setUserEmailVerified(Boolean userEmailVerified) {
        this.userEmailVerified = userEmailVerified;
        return this;
    }

    public final String getUserResetPasswordCode() {
        return userResetPasswordCode;
    }

    public final MyPetsResponse setUserResetPasswordCode(String userResetPasswordCode) {
        this.userResetPasswordCode = userResetPasswordCode;
        return this;
    }

    public final ZonedDateTime getUserResetPasswordCodeTimestamp() {
        return userResetPasswordCodeTimestamp;
    }

    public final MyPetsResponse setUserResetPasswordCodeTimestamp(ZonedDateTime userResetPasswordCodeTimestamp) {
        this.userResetPasswordCodeTimestamp = userResetPasswordCodeTimestamp;
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
        if (MyPetsResponse.class != obj.getClass()) {
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
        result = PRIME * result + ((this.petId == null) ? 0 : this.petId.hashCode());
        result = PRIME * result + ((this.petOwnerId == null) ? 0 : this.petOwnerId.hashCode());
        result = PRIME * result + ((this.petName == null) ? 0 : this.petName.hashCode());
        result = PRIME * result + ((this.petBirthdate == null) ? 0 : this.petBirthdate.hashCode());
        result = PRIME * result + ((this.petPetType == null) ? 0 : this.petPetType.hashCode());
        result = PRIME * result + ((this.petVaccinated == null) ? 0 : this.petVaccinated.hashCode());
        result = PRIME * result + ((this.userFirstName == null) ? 0 : this.userFirstName.hashCode());
        result = PRIME * result + ((this.userLastName == null) ? 0 : this.userLastName.hashCode());
        result = PRIME * result + ((this.userBirthdate == null) ? 0 : this.userBirthdate.hashCode());
        result = PRIME * result + ((this.userActive == null) ? 0 : this.userActive.hashCode());
        result = PRIME * result + ((this.userRole == null) ? 0 : this.userRole.hashCode());
        result = PRIME * result + ((this.userEmail == null) ? 0 : this.userEmail.hashCode());
        result = PRIME * result + ((this.userPasswordHash == null) ? 0 : this.userPasswordHash.hashCode());
        result = PRIME * result + ((this.userEmailVerificationCode == null) ? 0 : this.userEmailVerificationCode.hashCode());
        result = PRIME * result + ((this.userEmailVerificationCodeTimestamp == null) ? 0 : this.userEmailVerificationCodeTimestamp.hashCode());
        result = PRIME * result + ((this.userEmailVerified == null) ? 0 : this.userEmailVerified.hashCode());
        result = PRIME * result + ((this.userResetPasswordCode == null) ? 0 : this.userResetPasswordCode.hashCode());
        result = PRIME * result + ((this.userResetPasswordCodeTimestamp == null) ? 0 : this.userResetPasswordCodeTimestamp.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MyPetsResponse["
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
                + ", this.petId="
                + this.petId
                + ", this.petOwnerId="
                + this.petOwnerId
                + ", this.petName="
                + this.petName
                + ", this.petBirthdate="
                + this.petBirthdate
                + ", this.petPetType="
                + this.petPetType
                + ", this.petVaccinated="
                + this.petVaccinated
                + ", this.userFirstName="
                + this.userFirstName
                + ", this.userLastName="
                + this.userLastName
                + ", this.userBirthdate="
                + this.userBirthdate
                + ", this.userActive="
                + this.userActive
                + ", this.userRole="
                + this.userRole
                + ", this.userEmail="
                + this.userEmail
                + ", this.userEmailVerificationCodeTimestamp="
                + this.userEmailVerificationCodeTimestamp
                + ", this.userEmailVerified="
                + this.userEmailVerified
                + ", this.userResetPasswordCodeTimestamp="
                + this.userResetPasswordCodeTimestamp
                + "]";
    }
}
