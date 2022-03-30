package br.com.raospower.app.repositorys.models;

import br.com.raospower.app.repositorys.keys.RolePermissionKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity(name = "TB_RPM_ROLE_PERMISSION")
public class RolePermission implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private RolePermissionKey rolePermissionKey;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private Permission permission;

    public RolePermissionKey getRolePermissionKey() {
        return rolePermissionKey;
    }

    public void setRolePermissionKey(RolePermissionKey rolePermissionKey) {
        this.rolePermissionKey = rolePermissionKey;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
