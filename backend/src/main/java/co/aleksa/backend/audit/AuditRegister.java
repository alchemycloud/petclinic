package co.aleksa.backend.audit;

import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.PetHistoryRepository;
import co.aleksa.backend.repository.UserHistoryRepository;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class AuditRegister {
    @Inject private UserHistoryRepository userHistoryRepository;

    @Inject private PetHistoryRepository petHistoryRepository;

    @Inject private FlushFacade flushFacade;

    public void flushToDatabase(AuditContext context) {
        final Set<UserId> usersToLoad = new HashSet<>();
        final Set<PetId> petsToLoad = new HashSet<>();

        context.getUserChanges().forEach((entity, type) -> usersToLoad.add(entity.getId()));
        context.getPetChanges().forEach((entity, type) -> petsToLoad.add(entity.getId()));

        userHistoryRepository.findAllWithLastHistory(usersToLoad);
        petHistoryRepository.findAllWithLastHistory(petsToLoad);

        final Map<User, AuditType> auditedUsers = flushFacade.flushUsers(context.getUserChanges());
        final Map<Pet, AuditType> auditedPets = flushFacade.flushPets(context.getPetChanges());
    }
}
