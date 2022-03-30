package br.com.raospower.app.services;

import br.com.raospower.app.exceptions.PermissionNotFoundException;
import br.com.raospower.app.exceptions.RoleNotFoundException;
import br.com.raospower.app.repositorys.RolePermissionRepository;
import br.com.raospower.app.repositorys.keys.RolePermissionKey;
import br.com.raospower.app.repositorys.models.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    public List<RolePermission> getPermissionsByRole(Long id) {
        return repository.findRolePermissionByRoleId(id);
    }

    public List<RolePermission> getAll() {
        return repository.findAll();
    }

    public RolePermission findByRolePermission(List<String> roles, String permission, String methode) {
        return repository.findByRolePermission(roles, permission, methode);
    }

    public void associateRolePermission(Long roleId, Long permissionId) throws PermissionNotFoundException, RoleNotFoundException {
        // Validando existencia de perfis e permisssão.
        roleService.getRoleById(roleId);
        permissionService.getPermissionById(permissionId);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRolePermissionKey(new RolePermissionKey(roleId, permissionId));
        repository.save(rolePermission);
    }

    public void disassociateRolePermission(Long roleId, Long permissionId) throws PermissionNotFoundException, RoleNotFoundException {
        // Validando existencia de perfis e permisssão.
        roleService.getRoleById(roleId);
        permissionService.getPermissionById(permissionId);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRolePermissionKey(new RolePermissionKey(roleId, permissionId));
        repository.delete(rolePermission);
    }

}
