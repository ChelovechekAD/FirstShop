package com.example.firstshop.utils;

import com.example.firstshop.database.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims){
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(claims.get("roles", Set.class));
        jwtInfoToken.setEmail(claims.getSubject());
        jwtInfoToken.setUsername(claims.get("username", String.class));
        return jwtInfoToken;
    }
    private static Set<Role> getRoles(Claims claims){
        return claims.get("roles", Set.class);
    }
}
