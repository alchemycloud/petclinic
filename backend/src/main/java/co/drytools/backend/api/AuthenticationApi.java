package co.drytools.backend.api;

import co.drytools.backend.api.dto.authenticationapi.ChangePasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.ForgotPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.RefreshTokenRequest;
import co.drytools.backend.api.dto.authenticationapi.ResetPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInResponse;
import co.drytools.backend.api.dto.authenticationapi.SignUpRequest;
import co.drytools.backend.api.dto.authenticationapi.VerifyEmailRequest;
import co.drytools.backend.exception.ExceptionType;
import co.drytools.backend.model.User;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.UserRepository;
import co.drytools.backend.security.JWTService;
import co.drytools.backend.service.MailService;
import co.drytools.backend.service.SecurityService;
import co.drytools.backend.util.Require;
import io.jsonwebtoken.Claims;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
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

        final UserRole role = UserRole.VET;
        final String passwordHash = passwordEncoder.encode(dto.getPassword());
        final Optional<String> emailVerificationCode = Optional.of(RandomStringUtils.randomAlphanumeric(64));
        final Optional<ZonedDateTime> emailVerificationCodeTimestamp = Optional.of(ZonedDateTime.now(ZoneId.of("UTC")).plusDays(1));
        final Optional<String> resetPasswordCode = Optional.empty();
        final Optional<ZonedDateTime> resetPasswordCodeTimestamp = Optional.empty();
        final Boolean emailVerified = false;

        final String firstName = dto.getFirstName();
        final String lastName = dto.getLastName();
        final ZonedDateTime birthdate = dto.getBirthdate();
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

        final String responseAccessToken = jwtService.createAccessToken(Optional.of(model.getEmail()), model.getRole());
        final String responseRefreshToken = oldRefreshToken.orElseGet(() -> jwtService.createRefreshToken(Optional.of(model.getEmail())));

        final UserId responseId = model.getId();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final Boolean responseActive = model.getActive();
        final UserRole responseRole = model.getRole();
        final String responseEmail = model.getEmail();
        return new SignInResponse(
                responseAccessToken,
                responseRefreshToken,
                responseId,
                responseFirstName,
                responseLastName,
                responseBirthdate,
                responseActive,
                responseRole,
                responseEmail);
    }
}
