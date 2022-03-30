package br.com.raospower.app.services;

import br.com.raospower.app.exceptions.UserAlreadyExistsException;
import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.exceptions.UserNotInformedException;
import br.com.raospower.app.repositorys.UserRepository;
import br.com.raospower.app.repositorys.models.User;
import br.com.raospower.app.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(User user) throws UserNotInformedException, UserAlreadyExistsException {
        if (user == null) {
            throw new UserNotInformedException("Usuário não informado.");
        }
        User userRep = userRepository.findByUsername(user.getUsername());
        if (userRep != null) {
            throw new UserAlreadyExistsException("Usuário já cadastrado.");
        }
        userRepository.save(user);
    }

    public void update(User user) throws UserNotInformedException, UserNotFoundException {
        if (user == null) {
            throw new UserNotInformedException("Usuário não informado.");
        }
        // verificando existencia do usuário.
        this.getUserByID(user.getId());
        userRepository.save(user);
    }

    public void remove(long id) throws UserNotFoundException {
        Optional<User>  user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Usuário não localizado.");
        } else {
            remove(user.get());
        }
    }

    public void remove(User user) {
        userRepository.delete(user);
    }

    public User getUserByID(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Usuário inexistente");
        }
        return user.get();
    }

    public User getUserByUsername(String user) throws UserNotFoundException {
        User userRep = userRepository.findByUsername(user);
        if (userRep == null) {
            throw new UserNotFoundException("Usuário inexistente");
        }
        return userRep;
    }

    public List<User> getUsers(UserSpecification userSpecification) {
        return userRepository.findAll(userSpecification);
    }

}
