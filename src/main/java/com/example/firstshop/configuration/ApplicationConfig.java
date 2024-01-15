package com.example.firstshop.configuration;

import com.example.firstshop.repository.JwtTokensRepos;
import com.example.firstshop.repository.RoleRepository;
import com.example.firstshop.repository.UserRepository;
import com.example.firstshop.services.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public void getTwoKeys(){
        byte[] key_access = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        byte[] key_refresh = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String keyRefresh = Base64.getEncoder().encodeToString(key_refresh);
        String keyAccess = Base64.getEncoder().encodeToString(key_access);
        System.out.println("KeyAccess:  " + keyAccess);
        System.out.println("KeyRefresh:  " + keyRefresh);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User {%s} not found", username)));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
