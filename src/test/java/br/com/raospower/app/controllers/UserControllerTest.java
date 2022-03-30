package br.com.raospower.app.controllers;

import br.com.raospower.app.exceptions.UserAlreadyExistsException;
import br.com.raospower.app.exceptions.UserNotFoundException;
import br.com.raospower.app.exceptions.UserNotInformedException;
import br.com.raospower.app.repositorys.models.Role;
import br.com.raospower.app.repositorys.models.User;
import br.com.raospower.app.services.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private UserService usuarioService;

    @Test
    void insertUsuario() throws UserAlreadyExistsException {
        User usuario = null;
        try {
            usuario = usuarioService.getUserByUsername("raoni");
            if (usuario != null) {
                LOGGER.info("Usuário encontrado.");
                usuarioService.remove(usuario);
                LOGGER.info("Usuário removido.");
                usuario.setId(null);
                usuarioService.create(usuario);
                LOGGER.info("Usuário adicionado.");
            }
        } catch (UserNotFoundException e) {
            if (usuario == null) {
                usuario = new User();
                usuario.setName("Raoni");
                usuario.setEmail("raoni@raoni.com");
                usuario.setPassword("$2a$10$AClqa6pS99KTaT2QpbYdvuVP1yf3QOSBeHvzdjaphp5A3GY91yvEO");
                usuario.setUsername("raoni");
                Role prf = new Role();
                prf.setName("ROLE_USER");
                prf.setId(2L);
                usuario.getRoles().add(prf);

                try {
                    usuarioService.create(usuario);
                } catch (UserNotInformedException ex) {
                    assertFalse(true);
                }
            }
        } catch (UserNotInformedException e) {
            assertFalse(true);
        }
        try {
            usuario = usuarioService.getUserByUsername("raoni");
            if (usuario != null) {
                LOGGER.info(usuario.toString());
                List<Role> perfis = usuario.getRoles();
                for (Role perfil: perfis) {
                    LOGGER.info(perfil.toString());
                }
            }
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
        }
        assertTrue(true);
    }

    @Test
    void insertAdmin() throws UserAlreadyExistsException {
        User usuario = null;
        try {
            usuario = usuarioService.getUserByUsername("admin");
            if (usuario != null) {
                LOGGER.info("Usuário encontrado.");
                usuarioService.remove(usuario);
                LOGGER.info("Usuário removido.");
                usuario.setId(null);
                usuarioService.create(usuario);
                LOGGER.info("Usuário adicionado.");
            }
        } catch (UserNotFoundException e) {
            if (usuario == null) {
                usuario = new User();
                usuario.setName("admin");
                usuario.setEmail("admin@raoni.com");
                usuario.setPassword("$2a$10$AClqa6pS99KTaT2QpbYdvuVP1yf3QOSBeHvzdjaphp5A3GY91yvEO");
                usuario.setUsername("admin");
                Role prf = new Role();
                prf.setName("ROLE_ADMIN");
                prf.setId(1L);
                usuario.getRoles().add(prf);

                try {
                    usuarioService.create(usuario);
                } catch (UserNotInformedException ex) {
                    assertFalse(true);
                }
            }
        } catch (UserNotInformedException e) {
            assertFalse(true);
        }
        try {
            usuario = usuarioService.getUserByUsername("admin");
            if (usuario != null) {
                LOGGER.info(usuario.toString());
                List<Role> perfis = usuario.getRoles();
                for (Role perfil: perfis) {
                    LOGGER.info(perfil.toString());
                }
            }
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
        }
        assertTrue(true);
    }

}
