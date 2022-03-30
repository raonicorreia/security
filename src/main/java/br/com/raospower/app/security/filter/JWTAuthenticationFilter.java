package br.com.raospower.app.security.filter;

import br.com.raospower.app.exceptions.base.ExceptionUtil;
import br.com.raospower.app.security.cryption.AesCryption;
import br.com.raospower.app.security.custom.CustomUserDetails;
import br.com.raospower.app.security.models.TokenResponse;
import br.com.raospower.app.security.models.UserCredentials;
import br.com.raospower.app.util.PropertiesKeys;
import br.com.raospower.app.util.SecurityAppContext;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRATION = 1_600_000;

    public static final String KEY_SECRET = SecurityAppContext.getBean(Environment .class).getProperty(PropertiesKeys.JWT_SECRET_KEY);

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            String pass = null;
            if (credentials.getPassword() != null && !credentials.getPassword().isEmpty()) {
                try {
                    pass = AesCryption.getInstance().decrypt(credentials.getPassword());
                } catch (Exception e) {
                    throw new BadCredentialsException("Criptografia da senha inválida.", e);
                }
            } else {
                throw new BadCredentialsException("Uma senha deverá ser informada.");
            }
            return this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            pass
                    ));
        } catch (IOException e) {
            throw new BadCredentialsException("Falha durante autenticação.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        Date expire = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION);
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        String token = JWT.create().withSubject(userDetails.getUsername())
                .withExpiresAt(expire)
                .sign(Algorithm.HMAC512(KEY_SECRET));
        TokenResponse resp = new TokenResponse();
        resp.setAccessToken(token);
        resp.setTokenType("Bearer");
        // Seconds
        resp.setExpiresIn(TOKEN_EXPIRATION /1000);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
        response.getWriter().flush();

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        logger.info("Authentication failed");
        ExceptionUtil.responseErro(response, failed);
    }
}
