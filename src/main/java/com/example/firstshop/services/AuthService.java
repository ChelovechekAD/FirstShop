package com.example.firstshop.services;

import com.example.firstshop.components.JwtProvider;
import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.JwtRequest;
import com.example.firstshop.dto.response.JwtResponse;
import com.example.firstshop.exception.ListExceptions;
import com.example.firstshop.repository.UserRepository;
import com.example.firstshop.utils.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtResponse login(@NonNull JwtRequest request, @NonNull User user){
        System.out.println("Test: " + request.getPassword());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ListExceptions.WrongPasswordException("Неправильный пароль!");
        }
        String refreshToken = jwtProvider.generateRefreshToken(user);
        String accessToken = jwtProvider.generateAccessToken(user);
        refreshStorage.put(user.getEmail(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken){
        if(jwtProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(email);
            if(savedRefreshToken.equals(refreshToken)){
                final var user = userRepository.findUserByEmail(email)
                        .orElseThrow(()->new ListExceptions.UserNotFoundException("Пользователь не найден!"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refreshTokens(@NonNull String refreshToken){
        if (jwtProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(email);
            if (savedRefreshToken.equals(refreshToken)){
                final var user = userRepository.findUserByEmail(email)
                        .orElseThrow(()->new ListExceptions.UserNotFoundException("Пользователь не найден!"));
                final String access = jwtProvider.generateAccessToken(user);
                final String refresh = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), refresh);
                return new JwtResponse(access, refresh);
            }

        }
        throw new ListExceptions.UserAuthenticationException("Невалидный JWT токен");
    }

    public void logout(){
        SecurityContextHolder.clearContext();
    }

    public JwtAuthentication getUserInfo(){
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
