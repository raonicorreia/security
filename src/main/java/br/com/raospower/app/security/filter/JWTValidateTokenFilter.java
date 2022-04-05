package br.com.raospower.app.security.filter;

import br.com.raospower.app.security.custom.CustomUserDetailsService;
import br.com.raospower.app.security.util.SecurityAuthenticationEntryPoint;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTValidateTokenFilter extends BasicAuthenticationFilter {

    private static final String HEADER_ATTRIBUTE = "Authorization";

    private static final String ATTRIBUTE_PREFIX = "Bearer ";

    private final CustomUserDetailsService customUserDetailsService;

    public JWTValidateTokenFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager, new SecurityAuthenticationEntryPoint());
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String attribute = request.getHeader(HEADER_ATTRIBUTE);
            if (attribute == null) {
                chain.doFilter(request, response);
                return;
            }
            if (!attribute.startsWith(ATTRIBUTE_PREFIX)) {
                onUnsuccessfulAuthentication(request, response, new AuthenticationServiceException("Token não é do tipo Bearer."));
                return;
            }

            String token = attribute.replace(ATTRIBUTE_PREFIX, "");
            UsernamePasswordAuthenticationToken tokenUser = getAuthenticationToken(token);
            // Por padrão o BasicAuthenticationFilter só armazena o contexto para tokens do tipo Basic
            // Dessa forma precisamos forçar o armazenamento no contexto.
            SecurityContextHolder.getContext().setAuthentication(tokenUser);
            super.doFilterInternal(request, response, chain);
        } catch (JWTVerificationException e) {
            onUnsuccessfulAuthentication(request, response, new AuthenticationServiceException(e.getMessage(), e));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        try {
            DecodedJWT decode = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.KEY_SECRET)).build().verify(token);
            String username = decode.getSubject();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } catch (JWTVerificationException | IllegalArgumentException e) {
            logger.error("Falha validando token", e);
            throw e;
        }
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
        logger.info("[onUnsuccessfulAuthentication] " + failed.getMessage());
        try {
            super.getAuthenticationEntryPoint().
                    commence(request, response, failed);
        } catch (ServletException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
}
