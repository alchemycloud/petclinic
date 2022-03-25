package co.aleksa.administration.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RefreshTokenRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> REFRESH_TOKEN = new PropertyPath<>("refreshToken");

    @NotNull
    @Size(min = 64, max = 4096)
    private String refreshToken;

    private RefreshTokenRequest() {}

    public RefreshTokenRequest(String refreshToken) {
        this();
        this.refreshToken = refreshToken;
    }

    public final String getRefreshToken() {
        return refreshToken;
    }

    public final RefreshTokenRequest setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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
        if (RefreshTokenRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.refreshToken == null) ? 0 : this.refreshToken.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest";
    }
}
