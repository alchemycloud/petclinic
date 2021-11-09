package co.drytools.backend.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VerifyEmailRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> EMAIL_VERIFICATION_CODE = new PropertyPath<>("emailVerificationCode");

    @NotNull
    @Size(min = 64, max = 64)
    private String emailVerificationCode;

    private VerifyEmailRequest() {}

    public VerifyEmailRequest(String emailVerificationCode) {
        this();
        this.emailVerificationCode = emailVerificationCode;
    }

    public final String getEmailVerificationCode() {
        return emailVerificationCode;
    }

    public final VerifyEmailRequest setEmailVerificationCode(String emailVerificationCode) {
        this.emailVerificationCode = emailVerificationCode;
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
        if (VerifyEmailRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.emailVerificationCode == null) ? 0 : this.emailVerificationCode.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VerifyEmailRequest";
    }
}
