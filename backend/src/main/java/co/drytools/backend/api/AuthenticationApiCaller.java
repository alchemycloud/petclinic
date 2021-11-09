package co.drytools.backend.api;

import co.drytools.backend.api.dto.authenticationapi.ChangePasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.ForgotPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.RefreshTokenRequest;
import co.drytools.backend.api.dto.authenticationapi.ResetPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInResponse;
import co.drytools.backend.api.dto.authenticationapi.SignUpRequest;
import co.drytools.backend.api.dto.authenticationapi.VerifyEmailRequest;
import co.drytools.backend.audit.AuditFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private AuthenticationApiTransactionCaller authenticationApiTransactionCaller;

    public SignInResponse refreshToken(RefreshTokenRequest dto) {
        LOG.trace("refreshToken");

        final SignInResponse result = authenticationApiTransactionCaller.refreshToken(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public SignInResponse signIn(SignInRequest dto) {
        LOG.trace("signIn");

        final SignInResponse result = authenticationApiTransactionCaller.signIn(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void signUp(SignUpRequest dto) {
        LOG.trace("signUp");

        authenticationApiTransactionCaller.signUp(dto);
        auditFacade.flushAfterTransaction();
    }

    public void forgotPassword(ForgotPasswordRequest dto) {
        LOG.trace("forgotPassword");

        authenticationApiTransactionCaller.forgotPassword(dto);
        auditFacade.flushAfterTransaction();
    }

    public void resetPassword(ResetPasswordRequest dto) {
        LOG.trace("resetPassword");

        authenticationApiTransactionCaller.resetPassword(dto);
        auditFacade.flushAfterTransaction();
    }

    public void verifyEmail(VerifyEmailRequest dto) {
        LOG.trace("verifyEmail");

        authenticationApiTransactionCaller.verifyEmail(dto);
        auditFacade.flushAfterTransaction();
    }

    public void changePassword(ChangePasswordRequest dto) {
        LOG.trace("changePassword");

        authenticationApiTransactionCaller.changePassword(dto);
        auditFacade.flushAfterTransaction();
    }
}
