package co.drytools.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.model.id.VisitId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MyVisitsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<VisitId> ID = new PropertyPath<>("id");
    public static final PropertyPath<VetId> VET_ID = new PropertyPath<>("vetId");
    public static final PropertyPath<PetId> PET_ID = new PropertyPath<>("petId");
    public static final PropertyPath<Integer> VISIT_NUMBER = new PropertyPath<>("visitNumber");
    public static final PropertyPath<ZonedDateTime> TIMESTAMP = new PropertyPath<>("timestamp");
    public static final PropertyPath<BigDecimal> PET_WEIGHT = new PropertyPath<>("petWeight");
    public static final PropertyPath<String> DESCRIPTION = new PropertyPath<>("description");
    public static final PropertyPath<Boolean> SCHEDULED = new PropertyPath<>("scheduled");
    public static final PropertyPath<OwnerId> PET_OWNER_ID = new PropertyPath<>("petOwnerId");
    public static final PropertyPath<String> PET_NAME = new PropertyPath<>("petName");
    public static final PropertyPath<ZonedDateTime> PET_BIRTHDATE = new PropertyPath<>("petBirthdate");
    public static final PropertyPath<PetType> PET_PET_TYPE = new PropertyPath<>("petPetType");
    public static final PropertyPath<Boolean> PET_VACCINATED = new PropertyPath<>("petVaccinated");
    public static final PropertyPath<OwnerId> OWNER_ID = new PropertyPath<>("ownerId");
    public static final PropertyPath<UserId> OWNER_USER_ID = new PropertyPath<>("ownerUserId");
    public static final PropertyPath<String> OWNER_ADDRESS = new PropertyPath<>("ownerAddress");
    public static final PropertyPath<String> OWNER_CITY = new PropertyPath<>("ownerCity");
    public static final PropertyPath<String> OWNER_TELEPHONE = new PropertyPath<>("ownerTelephone");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
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

    @NotNull private VisitId id;

    @NotNull private VetId vetId;

    @NotNull private PetId petId;

    @NotNull private Integer visitNumber;

    @NotNull private ZonedDateTime timestamp;

    private BigDecimal petWeight;

    @Size(max = 1024)
    private String description;

    @NotNull private Boolean scheduled;

    @NotNull private OwnerId petOwnerId;

    @NotNull
    @Size(min = 2, max = 40)
    private String petName;

    @NotNull private ZonedDateTime petBirthdate;

    @NotNull private PetType petPetType;

    @NotNull private Boolean petVaccinated;

    @NotNull private OwnerId ownerId;

    @NotNull private UserId ownerUserId;

    @Size(min = 5, max = 100)
    private String ownerAddress;

    @Size(min = 2, max = 50)
    private String ownerCity;

    @Size(min = 5, max = 15)
    private String ownerTelephone;

    @NotNull private UserId userId;

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

    private MyVisitsResponse() {}

    public MyVisitsResponse(
            VisitId id,
            VetId vetId,
            PetId petId,
            Integer visitNumber,
            ZonedDateTime timestamp,
            BigDecimal petWeight,
            String description,
            Boolean scheduled,
            OwnerId petOwnerId,
            String petName,
            ZonedDateTime petBirthdate,
            PetType petPetType,
            Boolean petVaccinated,
            OwnerId ownerId,
            UserId ownerUserId,
            String ownerAddress,
            String ownerCity,
            String ownerTelephone,
            UserId userId,
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
        this.vetId = vetId;
        this.petId = petId;
        this.visitNumber = visitNumber;
        this.timestamp = timestamp;
        this.petWeight = petWeight;
        this.description = description;
        this.scheduled = scheduled;
        this.petOwnerId = petOwnerId;
        this.petName = petName;
        this.petBirthdate = petBirthdate;
        this.petPetType = petPetType;
        this.petVaccinated = petVaccinated;
        this.ownerId = ownerId;
        this.ownerUserId = ownerUserId;
        this.ownerAddress = ownerAddress;
        this.ownerCity = ownerCity;
        this.ownerTelephone = ownerTelephone;
        this.userId = userId;
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

    public final VisitId getId() {
        return id;
    }

    public final MyVisitsResponse setId(VisitId id) {
        this.id = id;
        return this;
    }

    public final VetId getVetId() {
        return vetId;
    }

    public final MyVisitsResponse setVetId(VetId vetId) {
        this.vetId = vetId;
        return this;
    }

    public final PetId getPetId() {
        return petId;
    }

    public final MyVisitsResponse setPetId(PetId petId) {
        this.petId = petId;
        return this;
    }

    public final Integer getVisitNumber() {
        return visitNumber;
    }

    public final MyVisitsResponse setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
        return this;
    }

    public final ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public final MyVisitsResponse setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public final BigDecimal getPetWeight() {
        return petWeight;
    }

    public final MyVisitsResponse setPetWeight(BigDecimal petWeight) {
        this.petWeight = petWeight;
        return this;
    }

    public final String getDescription() {
        return description;
    }

    public final MyVisitsResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public final Boolean getScheduled() {
        return scheduled;
    }

    public final MyVisitsResponse setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public final OwnerId getPetOwnerId() {
        return petOwnerId;
    }

    public final MyVisitsResponse setPetOwnerId(OwnerId petOwnerId) {
        this.petOwnerId = petOwnerId;
        return this;
    }

    public final String getPetName() {
        return petName;
    }

    public final MyVisitsResponse setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public final ZonedDateTime getPetBirthdate() {
        return petBirthdate;
    }

    public final MyVisitsResponse setPetBirthdate(ZonedDateTime petBirthdate) {
        this.petBirthdate = petBirthdate;
        return this;
    }

    public final PetType getPetPetType() {
        return petPetType;
    }

    public final MyVisitsResponse setPetPetType(PetType petPetType) {
        this.petPetType = petPetType;
        return this;
    }

    public final Boolean getPetVaccinated() {
        return petVaccinated;
    }

    public final MyVisitsResponse setPetVaccinated(Boolean petVaccinated) {
        this.petVaccinated = petVaccinated;
        return this;
    }

    public final OwnerId getOwnerId() {
        return ownerId;
    }

    public final MyVisitsResponse setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public final UserId getOwnerUserId() {
        return ownerUserId;
    }

    public final MyVisitsResponse setOwnerUserId(UserId ownerUserId) {
        this.ownerUserId = ownerUserId;
        return this;
    }

    public final String getOwnerAddress() {
        return ownerAddress;
    }

    public final MyVisitsResponse setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
        return this;
    }

    public final String getOwnerCity() {
        return ownerCity;
    }

    public final MyVisitsResponse setOwnerCity(String ownerCity) {
        this.ownerCity = ownerCity;
        return this;
    }

    public final String getOwnerTelephone() {
        return ownerTelephone;
    }

    public final MyVisitsResponse setOwnerTelephone(String ownerTelephone) {
        this.ownerTelephone = ownerTelephone;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final MyVisitsResponse setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final String getUserFirstName() {
        return userFirstName;
    }

    public final MyVisitsResponse setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public final String getUserLastName() {
        return userLastName;
    }

    public final MyVisitsResponse setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public final ZonedDateTime getUserBirthdate() {
        return userBirthdate;
    }

    public final MyVisitsResponse setUserBirthdate(ZonedDateTime userBirthdate) {
        this.userBirthdate = userBirthdate;
        return this;
    }

    public final Boolean getUserActive() {
        return userActive;
    }

    public final MyVisitsResponse setUserActive(Boolean userActive) {
        this.userActive = userActive;
        return this;
    }

    public final UserRole getUserRole() {
        return userRole;
    }

    public final MyVisitsResponse setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public final String getUserEmail() {
        return userEmail;
    }

    public final MyVisitsResponse setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public final String getUserPasswordHash() {
        return userPasswordHash;
    }

    public final MyVisitsResponse setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
        return this;
    }

    public final String getUserEmailVerificationCode() {
        return userEmailVerificationCode;
    }

    public final MyVisitsResponse setUserEmailVerificationCode(String userEmailVerificationCode) {
        this.userEmailVerificationCode = userEmailVerificationCode;
        return this;
    }

    public final ZonedDateTime getUserEmailVerificationCodeTimestamp() {
        return userEmailVerificationCodeTimestamp;
    }

    public final MyVisitsResponse setUserEmailVerificationCodeTimestamp(ZonedDateTime userEmailVerificationCodeTimestamp) {
        this.userEmailVerificationCodeTimestamp = userEmailVerificationCodeTimestamp;
        return this;
    }

    public final Boolean getUserEmailVerified() {
        return userEmailVerified;
    }

    public final MyVisitsResponse setUserEmailVerified(Boolean userEmailVerified) {
        this.userEmailVerified = userEmailVerified;
        return this;
    }

    public final String getUserResetPasswordCode() {
        return userResetPasswordCode;
    }

    public final MyVisitsResponse setUserResetPasswordCode(String userResetPasswordCode) {
        this.userResetPasswordCode = userResetPasswordCode;
        return this;
    }

    public final ZonedDateTime getUserResetPasswordCodeTimestamp() {
        return userResetPasswordCodeTimestamp;
    }

    public final MyVisitsResponse setUserResetPasswordCodeTimestamp(ZonedDateTime userResetPasswordCodeTimestamp) {
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
        if (MyVisitsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.vetId == null) ? 0 : this.vetId.hashCode());
        result = PRIME * result + ((this.petId == null) ? 0 : this.petId.hashCode());
        result = PRIME * result + ((this.visitNumber == null) ? 0 : this.visitNumber.hashCode());
        result = PRIME * result + ((this.timestamp == null) ? 0 : this.timestamp.hashCode());
        result = PRIME * result + ((this.petWeight == null) ? 0 : this.petWeight.hashCode());
        result = PRIME * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = PRIME * result + ((this.scheduled == null) ? 0 : this.scheduled.hashCode());
        result = PRIME * result + ((this.petOwnerId == null) ? 0 : this.petOwnerId.hashCode());
        result = PRIME * result + ((this.petName == null) ? 0 : this.petName.hashCode());
        result = PRIME * result + ((this.petBirthdate == null) ? 0 : this.petBirthdate.hashCode());
        result = PRIME * result + ((this.petPetType == null) ? 0 : this.petPetType.hashCode());
        result = PRIME * result + ((this.petVaccinated == null) ? 0 : this.petVaccinated.hashCode());
        result = PRIME * result + ((this.ownerId == null) ? 0 : this.ownerId.hashCode());
        result = PRIME * result + ((this.ownerUserId == null) ? 0 : this.ownerUserId.hashCode());
        result = PRIME * result + ((this.ownerAddress == null) ? 0 : this.ownerAddress.hashCode());
        result = PRIME * result + ((this.ownerCity == null) ? 0 : this.ownerCity.hashCode());
        result = PRIME * result + ((this.ownerTelephone == null) ? 0 : this.ownerTelephone.hashCode());
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
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
        return "MyVisitsResponse["
                + "this.id="
                + this.id
                + ", this.vetId="
                + this.vetId
                + ", this.petId="
                + this.petId
                + ", this.visitNumber="
                + this.visitNumber
                + ", this.timestamp="
                + this.timestamp
                + ", this.petWeight="
                + this.petWeight
                + ", this.description="
                + this.description
                + ", this.scheduled="
                + this.scheduled
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
                + ", this.ownerId="
                + this.ownerId
                + ", this.ownerUserId="
                + this.ownerUserId
                + ", this.ownerAddress="
                + this.ownerAddress
                + ", this.ownerCity="
                + this.ownerCity
                + ", this.ownerTelephone="
                + this.ownerTelephone
                + ", this.userId="
                + this.userId
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
