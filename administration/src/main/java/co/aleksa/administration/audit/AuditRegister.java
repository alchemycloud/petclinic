package co.aleksa.administration.audit;

import co.aleksa.administration.model.User;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.repository.UserHistoryRepository;
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
