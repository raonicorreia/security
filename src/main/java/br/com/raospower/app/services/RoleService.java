package br.com.raospower.app.services;

import br.com.raospower.app.exceptions.RoleNotFoundException;
import br.com.raospower.app.repositorys.RoleRepository;
import br.com.raospower.app.repositorys.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getAll() {
        return repository.findAll();
    }

    public Role getRoleById(Long id) throws RoleNotFoundException {
        Optional<Role> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new RoleNotFoundException("Perfil inexistente");
        }
        return optional.get();
    }
}
