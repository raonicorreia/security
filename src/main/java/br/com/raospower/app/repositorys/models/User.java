package br.com.raospower.app.repositorys.models;

import br.com.raospower.app.controllers.dto.UserDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TB_USR_USER")
public class User implements Serializable, ConverterDto<UserDTO> {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "SQ_USER")
    @SequenceGenerator(name = "SQ_USER", sequenceName = "SQ_USER", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_URL_USER_ROLE",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id"))
    private List<Role> roles = new ArrayList<>();

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public UserDTO convertToDto() {
        return null;
    }
}
