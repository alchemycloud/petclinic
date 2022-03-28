package co.aleksa.administration.api.dto.authenticationapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignInResponseTokens implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> TENANT = new PropertyPath<>("tenant");
    public static final PropertyPath<String> ACCESS_TOKEN = new PropertyPath<>("accessToken");
    public static final PropertyPath<String> REFRESH_TOKEN = new PropertyPath<>("refreshToken");

    @NotNull
    @Size(max = 255)
    private String tenant;

    @NotNull
    @Size(min = 64, max = 4096)
    private String accessToken;

    @NotNull
    @Size(min = 64, max = 4096)
    private String refreshToken;

    private SignInResponseTokens() {}

    public SignInResponseTokens(String tenant, String accessToken, String refreshToken) {
        this();
        this.tenant = tenant;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public final String getTenant() {
        return tenant;
    }

    public final SignInResponseTokens setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public final String getAccessToken() {
        return accessToken;
    }

    public final SignInResponseTokens setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public final String getRefreshToken() {
        return refreshToken;
    }

    public final SignInResponseTokens setRefreshToken(String refreshToken) {
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
        if (SignInResponseTokens.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.tenant == null) ? 0 : this.tenant.hashCode());
        result = PRIME * result + ((this.accessToken == null) ? 0 : this.accessToken.hashCode());
        result = PRIME * result + ((this.refreshToken == null) ? 0 : this.refreshToken.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SignInResponseTokens[" + "this.tenant=" + this.tenant + "]";
    }
}
