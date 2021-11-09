package co.drytools.backend.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignInRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> EMAIL = new PropertyPath<>("email");
    public static final PropertyPath<String> PASSWORD = new PropertyPath<>("password");

    @NotNull
    @Size(min = 6, max = 128)
    @Pattern(regexp = ".+\\@.+")
    private String email;

    @NotNull
    @Size(min = 6, max = 255)
    private String password;

    private SignInRequest() {}

    public SignInRequest(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public final String getEmail() {
        return email;
    }

    public final SignInRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public final String getPassword() {
        return password;
    }

    public final SignInRequest setPassword(String password) {
        this.password = password;
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
        if (SignInRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = PRIME * result + ((this.password == null) ? 0 : this.password.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SignInRequest[" + "this.email=" + this.email + "]";
    }
}
