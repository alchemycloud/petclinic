package co.drytools.backend.audit;

import co.drytools.backend.audit.changedtos.UpdatedFieldsDto;
import co.drytools.backend.model.User;
import co.drytools.backend.model.UserHistory;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.UserHistoryRepository;
import co.drytools.backend.repository.UserRepository;
import co.drytools.backend.util.AppThreadLocals;
import co.drytools.backend.util.TimeUtil;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class FlushFacade {
    @Inject private UserHistoryRepository userHistoryRepository;

    @Inject private UserRepository userRepository;

    Map<User, AuditType> flushUsers(Map<User, UpdatedFieldsDto> map) {
        return map.keySet().stream()
                .map(a -> flush(a, map.get(a)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(a -> a, a -> map.get(a).getAuditType()));
    }

    private Optional<User> flush(User entity, UpdatedFieldsDto updatedFieldsDto) {
        final AuditType auditType = updatedFieldsDto.getAuditType();

        if ((auditType == AuditType.UPDATE && updatedFieldsDto.isDirty()) || auditType == AuditType.CREATE) {
            final UserId entityId = entity.getId();
            final Optional<User> reference = Optional.of(entity);
            final ZonedDateTime changeTime = TimeUtil.now();
            final Optional<UserHistory> previous = entity.getLastHistory();
            final Optional<String> firstName = Optional.ofNullable(entity.getFirstName());
            final Optional<String> lastName = Optional.ofNullable(entity.getLastName());
            final Optional<ZonedDateTime> birthdate = Optional.ofNullable(entity.getBirthdate());
            final Optional<Boolean> active = Optional.ofNullable(entity.getActive());
            final Optional<UserRole> role = Optional.ofNullable(entity.getRole());
            final Optional<String> email = Optional.ofNullable(entity.getEmail());
            final Optional<String> passwordHash = Optional.ofNullable(entity.getPasswordHash());
            final Optional<String> emailVerificationCode = entity.getEmailVerificationCode();
            final Optional<ZonedDateTime> emailVerificationCodeTimestamp = entity.getEmailVerificationCodeTimestamp();
            final Optional<Boolean> emailVerified = Optional.ofNullable(entity.getEmailVerified());
            final Optional<String> resetPasswordCode = entity.getResetPasswordCode();
            final Optional<ZonedDateTime> resetPasswordCodeTimestamp = entity.getResetPasswordCodeTimestamp();

            final UserHistory audit =
                    userHistoryRepository.create(
                            Optional.of(AppThreadLocals.getCorrelationId()),
                            entityId.getValue(),
                            reference,
                            changeTime,
                            previous,
                            firstName,
                            lastName,
                            birthdate,
                            active,
                            role,
                            email,
                            passwordHash,
                            emailVerificationCode,
                            emailVerificationCodeTimestamp,
                            emailVerified,
                            resetPasswordCode,
                            resetPasswordCodeTimestamp);

            entity.setLastHistory(Optional.of(audit));
            userRepository.update(entity);
            return Optional.of(entity);
        } else if (auditType == AuditType.DELETE) {
            return Optional.of(entity);
        }

        return Optional.empty();
    }

    private boolean compareOptionalBigDecimal(Optional<BigDecimal> first, Optional<BigDecimal> second) {

        if (first.isEmpty() && second.isEmpty()) {
            return true;
        }
        if (first.isEmpty() && second.isPresent()) {
            return false;
        }
        if (first.isPresent() && second.isEmpty()) {
            return false;
        }

        return first.get().compareTo(second.get()) == 0;
    }
}
