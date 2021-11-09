package co.drytools.backend.api.dto.userapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateUserResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<ZonedDateTime> BIRTHDATE = new PropertyPath<>("birthdate");
    public static final PropertyPath<Boolean> ACTIVE = new PropertyPath<>("active");
    public static final PropertyPath<UserRole> ROLE = new PropertyPath<>("role");
    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<String> PASSWORD_HASH = new PropertyPath<>("passwordHash");
    public static final PropertyPath<String> EMAIL_VERIFICATION_CODE = new PropertyPath<>("emailVerificationCode");
    public static final PropertyPath<ZonedDateTime> EMAIL_VERIFICATION_CODE_TIMESTAMP = new PropertyPath<>("emailVerificationCodeTimestamp");
    public static final PropertyPath<Boolean> EMAIL_VERIFIED = new PropertyPath<>("emailVerified");
    public static final PropertyPath<String> RESET_PASSWORD_CODE = new PropertyPath<>("resetPasswordCode");
    public static final PropertyPath<ZonedDateTime> RESET_PASSWORD_CODE_TIMESTAMP = new PropertyPath<>("resetPasswordCodeTimestamp");

    @NotNull private UserId id;

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    @NotNull private ZonedDateTime birthdate;

    @NotNull private Boolean active;

    @NotNull private UserRole role;

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    @NotNull
    @Size(min = 6, max = 128)
    private String passwordHash;

    @Size(min = 64, max = 64)
    private String emailVerificationCode;

    private ZonedDateTime emailVerificationCodeTimestamp;

    @NotNull private Boolean emailVerified;

    @Size(min = 64, max = 64)
    private String resetPasswordCode;

    private ZonedDateTime resetPasswordCodeTimestamp;

    private UpdateUserResponse() {}

    public UpdateUserResponse(
            UserId id,
            String firstName,
            String lastName,
            ZonedDateTime birthdate,
            Boolean active,
            UserRole role,
            String email,
            String passwordHash,
            String emailVerificationCode,
            ZonedDateTime emailVerificationCodeTimestamp,
            Boolean emailVerified,
            String resetPasswordCode,
            ZonedDateTime resetPasswordCodeTimestamp) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.active = active;
        this.role = role;
        this.email = email;
        this.passwordHash = passwordHash;
        this.emailVerificationCode = emailVerificationCode;
        this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
        this.emailVerified = emailVerified;
        this.resetPasswordCode = resetPasswordCode;
        this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
    }

    public final UserId getId() {
        return id;
    }

    public final UpdateUserResponse setId(UserId id) {
        this.id = id;
        return this;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final UpdateUserResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public final String getLastName() {
        return lastName;
    }

    public final UpdateUserResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public final ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public final UpdateUserResponse setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public final Boolean getActive() {
        return active;
    }

    public final UpdateUserResponse setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public final UserRole getRole() {
        return role;
    }

    public final UpdateUserResponse setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public final String getEmail() {
        return email;
    }

    public final UpdateUserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public final String getPasswordHash() {
        return passwordHash;
    }

    public final UpdateUserResponse setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public final String getEmailVerificationCode() {
        return emailVerificationCode;
    }

    public final UpdateUserResponse setEmailVerificationCode(String emailVerificationCode) {
        this.emailVerificationCode = emailVerificationCode;
        return this;
    }

    public final ZonedDateTime getEmailVerificationCodeTimestamp() {
        return emailVerificationCodeTimestamp;
    }

    public final UpdateUserResponse setEmailVerificationCodeTimestamp(ZonedDateTime emailVerificationCodeTimestamp) {
        this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
        return this;
    }

    public final Boolean getEmailVerified() {
        return emailVerified;
    }

    public final UpdateUserResponse setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public final String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public final UpdateUserResponse setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
        return this;
    }

    public final ZonedDateTime getResetPasswordCodeTimestamp() {
        return resetPasswordCodeTimestamp;
    }

    public final UpdateUserResponse setResetPasswordCodeTimestamp(ZonedDateTime resetPasswordCodeTimestamp) {
        this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
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
        if (UpdateUserResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        result = PRIME * result + ((this.birthdate == null) ? 0 : this.birthdate.hashCode());
        result = PRIME * result + ((this.active == null) ? 0 : this.active.hashCode());
        result = PRIME * result + ((this.role == null) ? 0 : this.role.hashCode());
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = PRIME * result + ((this.passwordHash == null) ? 0 : this.passwordHash.hashCode());
        result = PRIME * result + ((this.emailVerificationCode == null) ? 0 : this.emailVerificationCode.hashCode());
        result = PRIME * result + ((this.emailVerificationCodeTimestamp == null) ? 0 : this.emailVerificationCodeTimestamp.hashCode());
        result = PRIME * result + ((this.emailVerified == null) ? 0 : this.emailVerified.hashCode());
        result = PRIME * result + ((this.resetPasswordCode == null) ? 0 : this.resetPasswordCode.hashCode());
        result = PRIME * result + ((this.resetPasswordCodeTimestamp == null) ? 0 : this.resetPasswordCodeTimestamp.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UpdateUserResponse["
                + "this.id="
                + this.id
                + ", this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + ", this.birthdate="
                + this.birthdate
                + ", this.active="
                + this.active
                + ", this.role="
                + this.role
                + ", this.email="
                + this.email
                + ", this.emailVerificationCodeTimestamp="
                + this.emailVerificationCodeTimestamp
                + ", this.emailVerified="
                + this.emailVerified
                + ", this.resetPasswordCodeTimestamp="
                + this.resetPasswordCodeTimestamp
                + "]";
    }
}
