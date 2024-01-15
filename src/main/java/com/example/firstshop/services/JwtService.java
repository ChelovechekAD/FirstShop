package com.example.firstshop.services;

import com.example.firstshop.components.JwtProvider;
import com.example.firstshop.controllers.JwtTokensController;
import com.example.firstshop.database.JwtToken;
import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.RefreshJwtRequest;
import com.example.firstshop.dto.response.JwtResponse;
import com.example.firstshop.repository.JwtTokensRepos;
import com.example.firstshop.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtTokensRepos jwtTokensRepos;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(JwtTokensController.class);
    public List<JwtToken> getAllExistTokens() {
        List<JwtToken> jwtTokens = new ArrayList<>();
        try {
            var jwtTokensList = jwtTokensRepos.findAll();
            for (var jwtToken : jwtTokensList) {
                jwtTokens.add(jwtToken);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jwtTokens;
    }

    public JwtResponse updateToken( RefreshJwtRequest request) {
        try {
            String token = request.getRefreshToken();
            if (jwtProvider.validateRefreshToken(token)) {
                String email = jwtProvider.getRefreshClaims(token).getSubject();
                if (userRepository.existsUserByEmail(email)) {
                    var reposTokenHash = jwtTokensRepos.findById(email);
                    if (reposTokenHash.isPresent()) {
                        JwtToken reposToken = reposTokenHash.get();
                        if (reposToken.getRefreshToken().equals(token)) {
                            jwtTokensRepos.delete(reposToken);
                            User user = userRepository.findByEmail(email);
                            String newAccessToken = jwtProvider.generateAccessToken(user);
                            String newRefreshToken = jwtProvider.generateRefreshToken(user);
                            jwtTokensRepos.save(JwtToken.builder()
                                    .id(email)
                                    .refreshToken(newRefreshToken)
                                    .build());
                            return new JwtResponse(newAccessToken, newRefreshToken);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JwtResponse(null, null);
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse loginToken(RefreshJwtRequest request) {
        try {
            String token = request.getRefreshToken();
            if (jwtProvider.validateRefreshToken(token)) {
                String email = jwtProvider.getRefreshClaims(token).getSubject();
                if (userRepository.existsUserByEmail(email)) {
                    var tokenRep = jwtTokensRepos.findById(email);
                    if (tokenRep.isPresent()) {
                        JwtToken tokenRepUnpack = tokenRep.get();
                        jwtTokensRepos.delete(tokenRepUnpack);
                    }
                    jwtTokensRepos.save(JwtToken.builder()
                            .id(email)
                            .refreshToken(token)
                            .build());
                    return new JwtResponse(null, token);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.error(e.getMessage());
            return new JwtResponse(null, null);
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse getTokens(RefreshJwtRequest request) throws ExpiredJwtException{
        try {
            String token = request.getRefreshToken();
            if (jwtProvider.validateRefreshToken(token)) {
                String email = jwtProvider.getRefreshClaims(token).getSubject();
                var jwtTokens = jwtTokensRepos.findById(email);
                if (jwtTokens.isPresent()) {
                    JwtToken jwtToken = jwtTokens.get();
                    System.out.println(jwtToken.getRefreshToken());
                    System.out.println(jwtToken.getId());
                } else {
                    System.out.println("Not found!" + token);
                }

            }
        } catch (ExpiredJwtException e) {
            System.out.println("Token Expired!");
            throw e;
        }
        return new JwtResponse(null, null);
    }

}
