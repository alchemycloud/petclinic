package co.aleksa.backend.audit;

import co.aleksa.backend.audit.changedtos.UpdatedFieldsDto;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.PetHistory;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.UserHistory;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.PetHistoryRepository;
import co.aleksa.backend.repository.PetRepository;
import co.aleksa.backend.repository.UserHistoryRepository;
import co.aleksa.backend.repository.UserRepository;
import co.aleksa.backend.util.AppThreadLocals;
import co.aleksa.backend.util.TimeUtil;
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

    @Inject private PetHistoryRepository petHistoryRepository;

    @Inject private PetRepository petRepository;

    Map<User, AuditType> flushUsers(Map<User, UpdatedFieldsDto> map) {
        return map.keySet().stream()
                .map(a -> flush(a, map.get(a)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(a -> a, a -> map.get(a).getAuditType()));
    }

    Map<Pet, AuditType> flushPets(Map<Pet, UpdatedFieldsDto> map) {
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
            final Optional<String> email = Optional.ofNullable(entity.getEmail());
            final Optional<ZonedDateTime> birthday = Optional.ofNullable(entity.getBirthday());
            final Optional<Boolean> active = Optional.ofNullable(entity.getActive());
            final Optional<UserRole> role = Optional.ofNullable(entity.getRole());

            final UserHistory audit =
                    userHistoryRepository.create(
                            Optional.of(AppThreadLocals.getCorrelationId()),
                            entityId.getValue(),
                            reference,
                            changeTime,
                            previous,
                            firstName,
                            lastName,
                            email,
                            birthday,
                            active,
                            role);

            entity.setLastHistory(Optional.of(audit));
            userRepository.update(entity);
            return Optional.of(entity);
        } else if (auditType == AuditType.DELETE) {
            return Optional.of(entity);
        }

        return Optional.empty();
    }

    private Optional<Pet> flush(Pet entity, UpdatedFieldsDto updatedFieldsDto) {
        final AuditType auditType = updatedFieldsDto.getAuditType();

        if ((auditType == AuditType.UPDATE && updatedFieldsDto.isDirty()) || auditType == AuditType.CREATE) {
            final PetId entityId = entity.getId();
            final Optional<Pet> reference = Optional.of(entity);
            final ZonedDateTime changeTime = TimeUtil.now();
            final Optional<PetHistory> previous = entity.getLastHistory();
            final Optional<Long> owner = Optional.ofNullable(entity.getOwnerId().getValue());
            final Optional<String> name = Optional.ofNullable(entity.getName());
            final Optional<ZonedDateTime> birthday = Optional.ofNullable(entity.getBirthday());
            final Optional<PetType> petType = Optional.ofNullable(entity.getPetType());
            final Optional<Boolean> vaccinated = Optional.ofNullable(entity.getVaccinated());

            final PetHistory audit =
                    petHistoryRepository.create(
                            Optional.of(AppThreadLocals.getCorrelationId()),
                            entityId.getValue(),
                            reference,
                            changeTime,
                            previous,
                            owner,
                            name,
                            birthday,
                            petType,
                            vaccinated);

            entity.setLastHistory(Optional.of(audit));
            petRepository.update(entity);
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
