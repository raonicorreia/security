package br.com.raospower.app.controllers;

import br.com.raospower.app.exceptions.UserAlreadyExistsException;
import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.exceptions.UserNotInformedException;
import br.com.raospower.app.repositorys.models.User;
import br.com.raospower.app.services.UserService;
import br.com.raospower.app.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasPermission('v1/users', 'GET')")
    public List<User> getUsers(@RequestBody User user) {
        UserSpecification userSpecification = new UserSpecification(user);
        return userService.getUsers(userSpecification);
    }

    // @PreAuthorize("@usuarioController.authorize('usuario/{id}:GET', authentication)")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasPermission({#id}, 'v1/users/{id}', 'GET')")
    public User getUserByID(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.getUserByID(id);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasPermission({#username}, 'v1/users/{username}', 'GET')")
    public User getUserByUsername(@PathVariable("username") String username) throws UserNotFoundException {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    @PreAuthorize("hasPermission('v1/users', 'POST')")
    public void create(@RequestBody User user) throws UserNotInformedException, UserAlreadyExistsException {
        userService.create(user);
    }

    @PutMapping
    @PreAuthorize("hasPermission('v1/users', 'PUT')")
    public void update(@RequestBody User user) throws UserNotInformedException, UserNotFoundException {
        userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasPermission({#id}, 'v1/users/{id}', 'DELETE')")
    public void remove(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.remove(id);
    }

}
