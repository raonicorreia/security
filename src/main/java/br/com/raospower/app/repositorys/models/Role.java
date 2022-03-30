package br.com.raospower.app.repositorys.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TB_ROL_ROLE")
public class Role implements Serializable {

    @Id
    @SequenceGenerator(name = "SQ_ROLE", sequenceName = "SQ_ROLE", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "SQ_ROLE")
    private Long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_RPM_ROLE_PERMISSION",
            joinColumns = @JoinColumn(name ="role_id"),
            inverseJoinColumns = @JoinColumn(name ="permission_id"))
    private List<Permission> permissionList = new ArrayList<>();

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

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
