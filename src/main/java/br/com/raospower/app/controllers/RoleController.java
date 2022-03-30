package br.com.raospower.app.controllers;

import br.com.raospower.app.exceptions.RoleNotFoundException;
import br.com.raospower.app.repositorys.models.Role;
import br.com.raospower.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/roles")
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    // @PreAuthorize("@perfilController.authorize('perfil/:GET', authentication)")
    @GetMapping
    @PreAuthorize("hasPermission('v1/roles', 'GET')")
    public List<Role> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('v1/roles/{id}', 'GET')")
    public Role getRoleById(@PathVariable("id") Long id) throws RoleNotFoundException {
        return service.getRoleById(id);
    }
}
