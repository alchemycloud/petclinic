package co.aleksa.administration.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ForgotPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    private ForgotPasswordRequest() {}

    public ForgotPasswordRequest(String email) {
        this();
        this.email = email;
    }

    public final String getEmail() {
        return email;
    }

    public final ForgotPasswordRequest setEmail(String email) {
        this.email = email;
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
        if (ForgotPasswordRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ForgotPasswordRequest[" + "this.email=" + this.email + "]";
    }
}
