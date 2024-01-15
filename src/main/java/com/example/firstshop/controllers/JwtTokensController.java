package com.example.firstshop.controllers;

import com.example.firstshop.components.JwtProvider;
import com.example.firstshop.database.JwtToken;
import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.RefreshJwtRequest;
import com.example.firstshop.dto.response.JwtResponse;
import com.example.firstshop.repository.JwtTokensRepos;
import com.example.firstshop.repository.UserRepository;
import com.example.firstshop.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/redis")
@Slf4j
public class JwtTokensController {
    private final JwtTokensRepos jwtTokensRepos;
    private final JwtProvider jwtProvider;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(JwtTokensController.class);

    @GetMapping(value = "/getAllExistTokens")
    @ResponseBody
    public ResponseEntity<List<JwtToken>> getAllExistTokens() {
        return ResponseEntity.ok(jwtService.getAllExistTokens());
    }

    @PostMapping(value = "/updateToken")
    @ResponseBody
    public ResponseEntity<JwtResponse> updateToken(@RequestBody RefreshJwtRequest request) {
        return ResponseEntity.ok(jwtService.updateToken(request));
    }

    @PostMapping(value = "/loginToken")
    @ResponseBody
    public ResponseEntity<JwtResponse> loginToken(@RequestBody RefreshJwtRequest request) {
        return ResponseEntity.ok(jwtService.loginToken(request));
    }

    @PostMapping(value = "/getTokens")
    @ResponseBody
    public ResponseEntity<JwtResponse> getTokens(@RequestBody RefreshJwtRequest request) {
        return ResponseEntity.ok(jwtService.getTokens(request));
    }

}
