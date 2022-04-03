package br.com.raospower.app.controllers;

import br.com.raospower.app.exceptions.UserAlreadyExistsException;
import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.exceptions.UserNotInformedException;
import br.com.raospower.app.services.UserService;
import br.com.raospower.app.services.dto.UserDTO;
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

    @GetMapping("/all")
    @PreAuthorize("hasPermission('v1/users', 'GET')")
    public List<UserDTO> getAllUsers() {
        return userService.getUsers(null);
    }

    @GetMapping
    @PreAuthorize("hasPermission('v1/users', 'GET')")
    public List<UserDTO> getUsers(@RequestBody UserDTO user) {
        UserSpecification userSpecification = new UserSpecification(user);
        return userService.getUsers(userSpecification);
    }

    // @PreAuthorize("@usuarioController.authorize('usuario/{id}:GET', authentication)")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasPermission({#id}, 'v1/users/{id}', 'GET')")
    public UserDTO getUserByID(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.getUserByID(id);
    }

    @PostMapping
    @PreAuthorize("hasPermission('v1/users', 'POST')")
    public void create(@RequestBody UserDTO user) throws UserNotInformedException, UserAlreadyExistsException {
        userService.create(user);
    }

    @PutMapping
    @PreAuthorize("hasPermission('v1/users', 'PUT')")
    @CrossOrigin(origins = "http://localhost")
    public void update(@RequestBody UserDTO user) throws UserNotInformedException, UserNotFoundException {
        userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasPermission({#id}, 'v1/users/{id}', 'DELETE')")
    public void remove(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.remove(id);
    }

}
