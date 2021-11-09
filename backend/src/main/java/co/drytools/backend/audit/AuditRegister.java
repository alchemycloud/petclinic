package co.drytools.backend.audit;

import co.drytools.backend.model.User;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.UserHistoryRepository;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class AuditRegister {
    @Inject private UserHistoryRepository userHistoryRepository;

    @Inject private FlushFacade flushFacade;

    public void flushToDatabase(AuditContext context) {
        final Set<UserId> usersToLoad = new HashSet<>();

        context.getUserChanges().forEach((entity, type) -> usersToLoad.add(entity.getId()));

        userHistoryRepository.findAllWithLastHistory(usersToLoad);

        final Map<User, AuditType> auditedUsers = flushFacade.flushUsers(context.getUserChanges());
    }
}
