package br.com.raospower.app.services;

import br.com.raospower.app.exceptions.UserAlreadyExistsException;
import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.exceptions.UserNotInformedException;
import br.com.raospower.app.repositorys.UserRepository;
import br.com.raospower.app.repositorys.models.User;
import br.com.raospower.app.services.dto.UserDTO;
import br.com.raospower.app.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserDTO user) throws UserNotInformedException, UserAlreadyExistsException {
        if (user == null) {
            throw new UserNotInformedException("Usuário não informado.");
        }
        User userRep = userRepository.findByUsername(user.getUsername());
        if (userRep != null) {
            throw new UserAlreadyExistsException("Usuário já cadastrado.");
        }
        // TODO a senha precisa chegar aqui criptografada.
        String encrypt = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encrypt);
        userRepository.save(user.convertUser());
    }

    public void update(UserDTO user) throws UserNotInformedException, UserNotFoundException {
        if (user == null) {
            throw new UserNotInformedException("Usuário não informado.");
        }
        // verificando existencia do usuário.
        this.getUserByID(user.getId());
        userRepository.save(user.convertUser());
    }

    public void remove(long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Usuário não localizado.");
        } else {
            remove(user.get().convertToDTO());
        }
    }

    public void remove(UserDTO user) {
        userRepository.delete(user.convertUser());
    }

    public UserDTO getUserByID(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Usuário inexistente");
        }
        return user.get().convertToDTO();
    }

    public UserDTO getUserByUsername(String user) throws UserNotFoundException {
        User userRep = userRepository.findByUsername(user);
        if (userRep == null) {
            throw new UserNotFoundException("Usuário inexistente");
        }
        return userRep.convertToDTO();
    }

    public List<UserDTO> getUsers(UserSpecification userSpecification) {
        List<User> user = null;
        if (userSpecification != null) {
            user = userRepository.findAll(userSpecification);
        } else {
            user = userRepository.findAll();
        }
        return (user != null) ? user.stream().map(User::convertToDTO).collect(Collectors.toList()) : null;
    }

}
