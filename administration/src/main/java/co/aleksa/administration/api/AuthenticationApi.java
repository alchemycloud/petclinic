package co.aleksa.administration.api;

import co.aleksa.administration.api.dto.authenticationapi.ChangePasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.ForgotPasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.RefreshTokenRequest;
import co.aleksa.administration.api.dto.authenticationapi.ResetPasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.SignInRequest;
import co.aleksa.administration.api.dto.authenticationapi.SignInResponse;
import co.aleksa.administration.api.dto.authenticationapi.SignInResponseTenants;
import co.aleksa.administration.api.dto.authenticationapi.SignInResponseTokens;
import co.aleksa.administration.api.dto.authenticationapi.SignUpRequest;
import co.aleksa.administration.api.dto.authenticationapi.VerifyEmailRequest;
import co.aleksa.administration.exception.ExceptionType;
import co.aleksa.administration.model.User;
import co.aleksa.administration.model.enumeration.UserRole;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.repository.UserRepository;
import co.aleksa.administration.security.JWTService;
import co.aleksa.administration.service.MailService;
import co.aleksa.administration.service.SecurityService;
import co.aleksa.administration.util.Require;
import io.jsonwebtoken.Claims;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationApi {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationApi.class);

    @Inject private JWTService jwtService;

    @Inject private UserRepository userRepository;

    @Inject private PasswordEncoder passwordEncoder;

    @Inject private MailService mailService;

    @Inject private SecurityService securityService;

    public SignInResponse refreshToken(RefreshTokenRequest dto) {
        LOG.trace("refreshToken");

        final Claims claims = jwtService.getJwtClaims(dto.getRefreshToken());
        Require.badRequestUnless(!claims.getExpiration().before(new Date()), ExceptionType.REFRESH_TOKEN_IS_EXPIRED);

        final User result = userRepository.findById(new UserId(Long.valueOf(claims.getSubject()))).get();
        return signInResponse(result, Optional.empty());
    }

    public SignInResponse signIn(SignInRequest dto) {
        LOG.trace("signIn");

        final Optional<User> user = userRepository.findByEmail(dto.getEmail());

        if (user.isEmpty()) return null;
        if (!passwordEncoder.matches(dto.getPassword(), user.get().getPasswordHash())) return null;

        Require.badRequestUnless(user.get().getEmailVerified(), ExceptionType.EMAIL_NOT_VERIFIED);

        final User result = userRepository.findById(user.get().getId()).get();
        return signInResponse(result, Optional.empty());
    }

    public void signUp(SignUpRequest dto) {
        LOG.trace("signUp");

        final Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        Require.badRequestUnless(optionalUser.isEmpty(), ExceptionType.EMAIL_ALREADY_IN_USER);

        final UserRole role = UserRole.NONE;
        final String passwordHash = passwordEncoder.encode(dto.getPassword());
        final Optional<String> emailVerificationCode = Optional.of(RandomStringUtils.randomAlphanumeric(64));
        final Optional<ZonedDateTime> emailVerificationCodeTimestamp = Optional.of(ZonedDateTime.now(ZoneId.of("UTC")).plusDays(1));
        final Optional<String> resetPasswordCode = Optional.empty();
        final Optional<ZonedDateTime> resetPasswordCodeTimestamp = Optional.empty();
        final Boolean emailVerified = false;

        final String firstName = dto.getFirstName();
        final String lastName = dto.getLastName();
        final ZonedDateTime birthday = dto.getBirthday();
        final Boolean active = dto.getActive();
        final String email = dto.getEmail();
        final User model =
                userRepository.create(null, null, null, null, null, null, null, Optional.empty(), Optional.empty(), null, Optional.empty(), Optional.empty());

        mailService.sendVerificationEmail(dto.getEmail(), model.getEmailVerificationCode().get(), Locale.ENGLISH);
    }

    public void forgotPassword(ForgotPasswordRequest dto) {
        LOG.trace("forgotPassword");

        final Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail()).filter(User::getEmailVerified);
        Require.badRequestUnless(optionalUser.isPresent(), ExceptionType.NONEXISTENT_EMAIL);

        final User model = optionalUser.get();
        final String resetPasswordCode = RandomStringUtils.randomAlphabetic(64);
        model.setResetPasswordCode(Optional.of(resetPasswordCode));
        model.setResetPasswordCodeTimestamp(Optional.of(ZonedDateTime.now(ZoneOffset.UTC).plusHours(1)));
        userRepository.update(model);

        mailService.sendResetPasswordEmail(model.getEmail(), resetPasswordCode, Locale.ENGLISH);
    }

    public void resetPassword(ResetPasswordRequest dto) {
        LOG.trace("resetPassword");

        final Optional<User> optionalUser = userRepository.findByResetPasswordCodeMandatory(dto.getResetPasswordCode());
        Require.badRequestUnless(optionalUser.isPresent(), ExceptionType.INVALID_RESET_PASSWORD_CODE);

        final User model = optionalUser.get();
        Require.badRequestUnless(
                !model.getResetPasswordCodeTimestamp().get().isBefore(ZonedDateTime.now(ZoneOffset.UTC)), ExceptionType.RESET_PASSWORD_CODE_IS_EXPIRED);

        model.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        model.setResetPasswordCodeTimestamp(Optional.empty());
        userRepository.update(model);
    }

    public void verifyEmail(VerifyEmailRequest dto) {
        LOG.trace("verifyEmail");

        final Optional<User> optionalUser = userRepository.findByEmailVerificationCodeMandatory(dto.getEmailVerificationCode());
        Require.badRequestUnless(optionalUser.isPresent(), ExceptionType.INVALID_EMAIL_VERIFICATION_CODE_ERROR);

        final User model = optionalUser.get();
        Require.badRequestUnless(
                !model.getEmailVerificationCodeTimestamp().get().isBefore(ZonedDateTime.now(ZoneId.of("UTC"))),
                ExceptionType.EMAIL_VERIFICATION_CODE_IS_EXPIRED);

        model.setEmailVerified(true);
        userRepository.update(model);
    }

    public void changePassword(ChangePasswordRequest dto) {
        LOG.trace("changePassword");
        // TODO check security constraints

        final User principal = securityService.getPrincipal().get();

        Require.badRequestUnless(principal != null, ExceptionType.CREDENTIALS_ARE_INVALID);
        Require.badRequestUnless(passwordEncoder.matches(dto.getOldPassword(), principal.getPasswordHash()), ExceptionType.CREDENTIALS_ARE_INVALID);

        principal.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.update(principal);
    }

    private SignInResponse signInResponse(User model, Optional<String> oldRefreshToken) {

        final String responseAccessToken = jwtService.createAccessToken(Optional.of(model.getEmail()), model.getRole(), Optional.empty());
        final String responseRefreshToken = oldRefreshToken.orElseGet(() -> jwtService.createRefreshToken(Optional.of(model.getEmail()), Optional.empty()));

        final List<SignInResponseTokens> responseTokens = null; // TODO set this field manually
        final String responseEmail = model.getEmail();
        final UserRole responseRole = model.getRole();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final List<SignInResponseTenants> responseTenants = null; // TODO set this field manually
        return new SignInResponse(
                responseAccessToken, responseRefreshToken, responseTokens, responseEmail, responseRole, responseFirstName, responseLastName, responseTenants);
    }
}
