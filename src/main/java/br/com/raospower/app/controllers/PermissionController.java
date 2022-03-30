package br.com.raospower.app.controllers;

import br.com.raospower.app.exceptions.PermissionNotFoundException;
import br.com.raospower.app.exceptions.PermissionNotInformedException;
import br.com.raospower.app.repositorys.models.Permission;
import br.com.raospower.app.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/permissions")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService service;

    // @PreAuthorize("@perfilController.authorize('permissao/:GET', authentication)")
    @GetMapping
    @PreAuthorize("hasPermission('v1/permissions', 'GET')")
    public List<Permission> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('v1/permissions/{id}', 'GET')")
    public Permission getPermissionById(@PathVariable("id") Long id) throws PermissionNotFoundException {
        return service.getPermissionById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission('v1/permissions/{id}', 'DELETE')")
    public void remove(@PathVariable("id") Long id) throws PermissionNotFoundException {
        service.remove(id);
    }

    @PostMapping
    @PreAuthorize("hasPermission('v1/permissions', 'POST')")
    public void create(@RequestBody Permission permission) throws PermissionNotInformedException {
        service.create(permission);
    }

    @PutMapping
    @PreAuthorize("hasPermission('v1/permissions', 'PUT')")
    public void update(@RequestBody Permission permission) throws PermissionNotInformedException, PermissionNotFoundException {
        service.update(permission);
    }
}
