package PruebaT.ecommerce.security.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * Filtro para la autenticación JWT en cada solicitud HTTP.
 *
 * Utiliza {@link JwtService} para manejar los tokens JWT.
 * Utiliza {@link UserDetailsService} para cargar los detalles del usuario manejo de sesion de spring security.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuth extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Filtra cada solicitud HTTP para autenticar al usuario basado en el token JWT.
     *
     * @param request el objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response el objeto HttpServletResponse que contiene la respuesta del servidor.
     * @param filterChain el objeto FilterChain para pasar la solicitud y respuesta al siguiente filtro.
     * @throws ServletException si ocurre un error en el procesamiento del filtro.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        final String username;
        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }
        username=jwtService.getUsernameFromToken(token);
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Obtiene el token JWT de la solicitud HTTP.
     *
     * @param request el objeto HttpServletRequest que contiene la solicitud del cliente.
     * @return el token JWT si está presente en la cabecera de autorización, de lo contrario null.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;

    }
}
