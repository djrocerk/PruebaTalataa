package PruebaT.ecommerce.security.service;

import PruebaT.ecommerce.security.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * Servicio para la generación y validación de tokens JWT.
 *
 * Utiliza {@link User} para obtener la información del usuario.
 * Utiliza {@link Claims} para manejar las reclamaciones del token JWT.
 * Utiliza {@link Jwts} para construir y analizar los tokens JWT.
 * Utiliza {@link SignatureAlgorithm} para definir el algoritmo de firma del token.
 * Utiliza {@link Keys} para generar la clave de firma.
 * Utiliza {@link UserDetails} para obtener los detalles del usuario.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Service
public class JwtService {

    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Genera un token JWT para un usuario.
     *
     * @param user el objeto User que contiene la información del usuario.
     * @return el token JWT generado.
     */
    public String getToken(User user){
        return genereToken(new HashMap<>(),user);
    }

    /**
     * Genera un token JWT con reclamaciones adicionales.
     *
     * @param extraClaims un mapa de reclamaciones adicionales.
     * @param user el objeto User que contiene la información del usuario.
     * @return el token JWT generado.
     */
    private String genereToken(Map<String, Object> extraClaims, User user){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene la clave de firma para el token JWT.
     *
     * @return la clave de firma.
     */
    private Key getkey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el nombre de usuario del token JWT.
     *
     * @param token el token JWT.
     * @return el nombre de usuario.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si el token JWT es válido.
     *
     * @param token el token JWT.
     * @param userDetails los detalles del usuario.
     * @return true si el token es válido, de lo contrario false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    /**
     * Obtiene todas las reclamaciones del token JWT.
     *
     * @param token el token JWT.
     * @return las reclamaciones del token.
     */
    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene una reclamación específica del token JWT.
     *
     * @param token el token JWT.
     * @param claimsResolver la función para resolver la reclamación.
     * @param <T> el tipo de la reclamación.
     * @return la reclamación resuelta.
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene la fecha de expiración del token JWT.
     *
     * @param token el token JWT.
     * @return la fecha de expiración.
     */
    Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token el token JWT.
     * @return true si el token ha expirado, de lo contrario false.
     */
    boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
}
