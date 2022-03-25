package co.aleksa.administration.service;

import co.aleksa.administration.model.User;
import co.aleksa.administration.repository.UserRepository;
import co.aleksa.administration.util.AppThreadLocals;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Inject private UserRepository userRepository;

    public Optional<User> getPrincipal() {

        final Optional<User> alreadyLoadedPrincipal = AppThreadLocals.getPrincipal();

        if (alreadyLoadedPrincipal.isPresent()) {
            return alreadyLoadedPrincipal;
        }
        if (AppThreadLocals.getPrincipalEmail().isPresent()) {
            Optional<User> principal = userRepository.findByEmail(AppThreadLocals.getPrincipalEmail().get());
            AppThreadLocals.setPrincipal(principal.get());
            return principal;
        }
        return Optional.empty();
    }
}
