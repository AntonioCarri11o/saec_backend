package com.saec.formtic.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    /**
     * Crear un token JWT para los detalles de autenticación proporcionados.
     *
     * @param authentication Objeto que contiene los detalles del usaurio y sus authorities.
     * @return JWT token firmado como String
     */
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.expiration))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    /***
     * Validar que el token use las claves privadas del sistema bajo el mismo tipo de cifrado.
     *
     * @param token cifrado
     * @return token descifrado
     * @throws JWTVerificationException en caso de que el token sea invalido
     */
    public DecodedJWT validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Token invalid, not authorized");
        }
    }

    /***
     * Obtener la clave de acceso del usuario (correo para admins; no. de empleado para empleados)
     *
     * @param decodedJWT token descifrado
     * @return Clave de acceso del usuario
     */
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    /***
     * Obtener claims especificas
     *
     * @param decodedJWT token descifrado
     * @param claimName nombre de la claim a obtener
     * @return Objeto de tipo Claim que se solicitö
     */
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
}
