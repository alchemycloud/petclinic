package co.drytools.backend.service;

import co.drytools.backend.model.User;
import co.drytools.backend.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Inject private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.trace(".loadUserByUsername({})", username);

        final String lowercaseUsername = username.toLowerCase();
        final Optional<User> optionalUser = userRepository.findByEmail(lowercaseUsername);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }

        final User user = optionalUser.get();
        final List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(lowercaseUsername, null, grantedAuthorities);
    }
}
