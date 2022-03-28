package co.aleksa.administration.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> OLD_PASSWORD = new PropertyPath<>("oldPassword");
    public static final PropertyPath<String> NEW_PASSWORD = new PropertyPath<>("newPassword");

    @NotNull
    @Size(min = 12, max = 128)
    private String oldPassword;

    @NotNull
    @Size(min = 12, max = 128)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){12,128}$")
    private String newPassword;

    private ChangePasswordRequest() {}

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this();
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public final String getOldPassword() {
        return oldPassword;
    }

    public final ChangePasswordRequest setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public final String getNewPassword() {
        return newPassword;
    }

    public final ChangePasswordRequest setNewPassword(String newPassword) {
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
        if (ChangePasswordRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.oldPassword == null) ? 0 : this.oldPassword.hashCode());
        result = PRIME * result + ((this.newPassword == null) ? 0 : this.newPassword.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest";
    }
}
