package com.saec.formtic.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.saec.formtic.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            DecodedJWT decodedToken = jwtService.validateToken(token);

            // Extraer el username y authorities del token descifrado
            String username = jwtService.extractUsername(decodedToken);
            String stringAuthorities = jwtService.getSpecificClaim(decodedToken, "authorities").asString();

            // Convertir las authorities en una coleccion que Spring Security comprenda
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.
                    commaSeparatedStringToAuthorityList(stringAuthorities);

            // Genera el contexto de seguridad y asigna la autenticacion obtenida
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
        }
        // Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }
}
