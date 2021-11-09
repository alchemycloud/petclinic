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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private AuthenticationApi authenticationApi;

    @Transactional
    public SignInResponse refreshToken(RefreshTokenRequest dto) {
        LOG.trace("refreshToken");

        final SignInResponse result = authenticationApi.refreshToken(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public SignInResponse signIn(SignInRequest dto) {
        LOG.trace("signIn");

        final SignInResponse result = authenticationApi.signIn(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void signUp(SignUpRequest dto) {
        LOG.trace("signUp");

        authenticationApi.signUp(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest dto) {
        LOG.trace("forgotPassword");

        authenticationApi.forgotPassword(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest dto) {
        LOG.trace("resetPassword");

        authenticationApi.resetPassword(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional
    public void verifyEmail(VerifyEmailRequest dto) {
        LOG.trace("verifyEmail");

        authenticationApi.verifyEmail(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional
    public void changePassword(ChangePasswordRequest dto) {
        LOG.trace("changePassword");

        authenticationApi.changePassword(dto);
        auditFacade.flushInTransaction();
    }
}
