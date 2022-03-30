package br.com.raospower.app.security.custom;

import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.repositorys.models.User;
import br.com.raospower.app.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService usuarioService;

    public CustomUserDetailsService(UserService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = Optional.of(usuarioService.getUserByUsername(username));
            return new CustomUserDetails(user);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("Usuario não encontrado.", e);
        }
    }
}
