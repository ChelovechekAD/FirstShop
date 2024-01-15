package com.example.firstshop.utils;

import com.example.firstshop.database.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private String email;
    private String username;
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }

    @Override
    public boolean isAuthenticated(){
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthentication) throws IllegalArgumentException{
        this.authenticated = isAuthentication;
    }
}
