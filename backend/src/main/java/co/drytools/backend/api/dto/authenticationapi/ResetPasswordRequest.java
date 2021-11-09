package co.drytools.backend.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> RESET_PASSWORD_CODE = new PropertyPath<>("resetPasswordCode");
    public static final PropertyPath<String> NEW_PASSWORD = new PropertyPath<>("newPassword");

    @NotNull
    @Size(min = 64, max = 64)
    private String resetPasswordCode;

    @NotNull
    @Size(min = 6, max = 255)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z*&@%+/_'!#$^?:.(\\)\\[\\]{}~\\-]{8,}$")
    private String newPassword;

    private ResetPasswordRequest() {}

    public ResetPasswordRequest(String resetPasswordCode, String newPassword) {
        this();
        this.resetPasswordCode = resetPasswordCode;
        this.newPassword = newPassword;
    }

    public final String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public final ResetPasswordRequest setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
        return this;
    }

    public final String getNewPassword() {
        return newPassword;
    }

    public final ResetPasswordRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
        if (ResetPasswordRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.resetPasswordCode == null) ? 0 : this.resetPasswordCode.hashCode());
        result = PRIME * result + ((this.newPassword == null) ? 0 : this.newPassword.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest";
    }
}
