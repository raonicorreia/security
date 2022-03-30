package br.com.raospower.app.security.custom;

import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.services.UserService;
import br.com.raospower.app.services.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserDTO> user = Optional.of(userService.getUserByUsername(username));
            return new CustomUserDetails(user);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("Usuario não encontrado.", e);
        }
    }
}
