package br.com.raospower.app.controllers;

import br.com.raospower.app.exceptions.PermissionNotFoundException;
import br.com.raospower.app.exceptions.RoleNotFoundException;
import br.com.raospower.app.repositorys.models.RolePermission;
import br.com.raospower.app.services.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/roles-permissions")
public class RolePermissionController extends BaseController {

    @Autowired
    private RolePermissionService service;

    @GetMapping(value = "/roles/{id}")
    @PreAuthorize("hasPermission('v1/roles-permissions/roles/{id}', 'GET')")
    public List<RolePermission> getPermissionsByRole(@PathVariable("id") Long id) {
        return service.getPermissionsByRole(id);
    }

    @GetMapping
    @PreAuthorize("hasPermission('v1/roles-permissions', 'GET')")
    public List<RolePermission> getAll() {
        return service.getAll();
    }

    @PostMapping("/roles/{idRole}/permissions/{idPermission}")
    @PreAuthorize("hasPermission('v1/roles-permissions/roles/{idRole}/permissions/{idPermission}', 'POST')")
    public void associateRolePermission(
            @PathVariable("idRole") Long idRoles,
            @PathVariable("idPermission") Long idPermission)
            throws PermissionNotFoundException, RoleNotFoundException {
        service.associateRolePermission(idRoles, idPermission);
    }

    @DeleteMapping("/roles/{idRole}/permissions/{idPermission}")
    @PreAuthorize("hasPermission('v1/roles-permissions/roles/{idRole}/permissions/{idPermission}', 'DELETE')")
    public void disassociateRolePermission(
            @PathVariable("idRole") Long idRoles,
            @PathVariable("idPermission") Long idPermission)
            throws PermissionNotFoundException, RoleNotFoundException {
        service.disassociateRolePermission(idRoles, idPermission);
    }
}
