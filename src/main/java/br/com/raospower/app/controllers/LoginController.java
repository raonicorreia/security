package br.com.raospower.app.controllers;

import br.com.raospower.app.security.models.TokenResponse;
import br.com.raospower.app.security.models.UserCredentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping
    public TokenResponse login(@RequestBody UserCredentials userCredentials) {
        return null;
    }
}
