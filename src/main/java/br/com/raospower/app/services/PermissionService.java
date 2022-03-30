package br.com.raospower.app.services;

import br.com.raospower.app.exceptions.PermissionNotFoundException;
import br.com.raospower.app.exceptions.PermissionNotInformedException;
import br.com.raospower.app.repositorys.PermissionRepository;
import br.com.raospower.app.repositorys.models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    public void create(Permission permission) throws PermissionNotInformedException {
        if (permission == null) {
            throw new PermissionNotInformedException("Permissão não informada.");
        }
        repository.save(permission);
    }

    public void update(Permission permission) throws PermissionNotInformedException, PermissionNotFoundException {
        if (permission == null) {
            throw new PermissionNotInformedException("Permissão não informada.");
        }
        this.getPermissionById(permission.getId());
        repository.save(permission);
    }

    public void remove(Long id) throws PermissionNotFoundException {
        Permission permission = this.getPermissionById(id);
        repository.delete(permission);
    }

    public List<Permission> getAll() {
        return repository.findAll();
    }

    public Permission getPermissionById(Long id) throws PermissionNotFoundException {
        Optional<Permission> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new PermissionNotFoundException("Permissão inexistente.");
        }
        return optional.get();
    }
}
