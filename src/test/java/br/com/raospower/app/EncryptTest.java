package br.com.raospower.app;

import br.com.raospower.app.security.cryption.CryptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EncryptTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptTest.class);

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Teste criptografia BCrypt")
    void encrypt1() {
        String textPlain = "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode(textPlain);
        LOGGER.info("BCrypt: " + result);
        assertTrue(encoder.matches(textPlain, result));
    }

}
