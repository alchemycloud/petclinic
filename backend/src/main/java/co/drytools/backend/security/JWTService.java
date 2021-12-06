package co.drytools.backend.security;

import co.drytools.backend.config.CustomProperties;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.util.TimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private static final String SEPARATOR = ",";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String REFRESH_AUTHORITY = "refresh";
    private static final String PRINCIPAL_EMAIL = "principalEmail";

    @Inject private CustomProperties customProperties;

    public String createAccessToken(Optional<String> principalEmail, UserRole role) {
        return Jwts.builder()
                .claim(PRINCIPAL_EMAIL, principalEmail.orElse(null))
                .claim(AUTHORITIES_KEY, role)
                .signWith(SignatureAlgorithm.HS512, customProperties.getSecretKey())
                .setExpiration(getValidity(customProperties.getAccessTokenValidityInSeconds()))
                .compact();
    }

    public String createRefreshToken(Optional<String> principalEmail) {
        return Jwts.builder()
                .claim(PRINCIPAL_EMAIL, principalEmail.orElse(null))
                .claim(AUTHORITIES_KEY, REFRESH_AUTHORITY)
                .signWith(SignatureAlgorithm.HS512, customProperties.getSecretKey())
                .setExpiration(getValidity(customProperties.getRefreshTokenValidityInSeconds()))
                .compact();
    }

    Authentication getAuthentication(String token) {
        final Claims claims = getJwtClaims(token);
        final String role = claims.get(AUTHORITIES_KEY).toString();
        final Optional<String> principalEmail = Optional.ofNullable(claims.get(PRINCIPAL_EMAIL)).map(Object::toString);

        return new PreAuthenticatedAuthenticationToken(principalEmail.orElse(null), null, Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    public Claims getJwtClaims(String token) {
        return Jwts.parser().setSigningKey(customProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }

    private Date getValidity(long refreshTokenValidity) {
        return Date.from(TimeUtil.now().plusSeconds(refreshTokenValidity).toInstant());
    }
}
