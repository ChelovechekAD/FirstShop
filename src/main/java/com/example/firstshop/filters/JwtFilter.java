package com.example.firstshop.filters;

import com.example.firstshop.components.JwtProvider;
import com.example.firstshop.database.Role;
import com.example.firstshop.database.User;
import com.example.firstshop.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final static String AUTHORIZATION = "Authorization";
    private String  getTokenFromRequest(HttpServletRequest request){
        final String bearer = request.getHeader(AUTHORIZATION);
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String userEmail;
        if (!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtProvider.getAccessClaims(jwt).getSubject();
        if(StringUtils.hasLength(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null){
            User  user = userRepository.findByEmail(userEmail);
            // TODO: Change validateToken
            if (jwtProvider.validateAccessToken(jwt, user.getEmail())){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(token);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }



    /*
    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if(token!=null && jwtProvider.validateAccessToken(token)){
            final Claims claims = jwtProvider.getAccessClaims(token);
            final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

     */
}
