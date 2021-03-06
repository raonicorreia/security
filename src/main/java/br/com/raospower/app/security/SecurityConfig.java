package br.com.raospower.app.security;

import br.com.raospower.app.security.custom.CustomUserDetailsService;
import br.com.raospower.app.security.filter.JWTAuthenticationFilter;
import br.com.raospower.app.security.filter.JWTValidateTokenFilter;
import br.com.raospower.app.security.util.SecurityAccessDeniedHandler;
import br.com.raospower.app.security.util.SecurityAuthenticationEntryPoint;
import br.com.raospower.app.security.util.SecurityPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public SecurityPermissionEvaluator customPermissionEvaluator() {
        return new SecurityPermissionEvaluator();
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator());
        return expressionHandler;
    }

    // Devido a customiza????o do tratamento de permiss??o GlobalMethodSecurityConfiguration
    // foi necess??rio separar o modulo de configura????o do WebSecurityConfigurerAdapter
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTValidateTokenFilter(authenticationManager(), customUserDetailsService))
                    .exceptionHandling()
                        .accessDeniedHandler(new SecurityAccessDeniedHandler())
                        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                    .and().authorizeRequests()
                        .antMatchers( "/login/**").permitAll()
                        .antMatchers( "/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**").permitAll()
                        .anyRequest().authenticated();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList("*"));
            corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }
    }
}
