package co.aleksa.administration.rest;

import co.aleksa.administration.api.AuthenticationApiCaller;
import co.aleksa.administration.api.dto.authenticationapi.ChangePasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.ForgotPasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.RefreshTokenRequest;
import co.aleksa.administration.api.dto.authenticationapi.ResetPasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.SignInRequest;
import co.aleksa.administration.api.dto.authenticationapi.SignInResponse;
import co.aleksa.administration.api.dto.authenticationapi.SignUpRequest;
import co.aleksa.administration.api.dto.authenticationapi.VerifyEmailRequest;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AuthenticationApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationApiResource.class);

    @Inject private AuthenticationApiCaller authenticationApiCaller;

    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignInResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LOG.debug("POST /refresh-token {}", request);

        final SignInResponse response = authenticationApiCaller.refreshToken(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request, Errors errors) {
        LOG.debug("POST /sign-in {}", request);

        final SignInResponse response = authenticationApiCaller.signIn(request);
        if (response != null) return ResponseEntity.ok().body(response);
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        LOG.debug("POST /sign-up {}", request);

        authenticationApiCaller.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        LOG.debug("POST /forgot-password {}", request);

        authenticationApiCaller.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        LOG.debug("POST /reset-password {}", request);

        authenticationApiCaller.resetPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/verify-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        LOG.debug("POST /verify-email {}", request);

        authenticationApiCaller.verifyEmail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        LOG.debug("POST /change-password {}", request);

        authenticationApiCaller.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
