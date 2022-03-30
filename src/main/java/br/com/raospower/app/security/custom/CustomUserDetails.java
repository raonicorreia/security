package br.com.raospower.app.security.custom;

import br.com.raospower.app.repositorys.models.Role;
import br.com.raospower.app.repositorys.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomUserDetails implements UserDetails, Serializable {

    private Optional<User> user;

    public CustomUserDetails(Optional<User> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> perfis = user.orElse(new User()).getRoles();
        return perfis.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }

    public long getId() {
        return user.orElse(new User()).getId();
    }

    @Override
    public String getPassword() {
        return user.orElse(new User()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new User()).getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
