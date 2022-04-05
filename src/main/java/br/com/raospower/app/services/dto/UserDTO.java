package br.com.raospower.app.services.dto;

import br.com.raospower.app.repositorys.models.Role;
import br.com.raospower.app.repositorys.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private List<RoleDTO> roles = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
        this.setId(user.getId());
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Role role: user.getRoles()) {
                this.getRoles().add(new RoleDTO(role));
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public User convertUser() {
        User user = new User();
        user.setPassword(this.password);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setUsername(this.username);
        user.setId(this.id);
        if (this.roles != null && !this.roles.isEmpty()) {
            for (RoleDTO roleDTO: this.roles) {
                user.getRoles().add(new Role(roleDTO));
            }
        }
        return user;
    }

}
