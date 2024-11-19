package PruebaT.ecommerce.security.service;

import PruebaT.ecommerce.security.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testGetToken() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        String token = jwtService.getToken(user);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void testGetUsernameFromToken() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        String token = jwtService.getToken(user);
        String username = jwtService.getUsernameFromToken(token);

        assertEquals("testuser", username);
    }

    @Test
    void testIsTokenValid() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        String token = jwtService.getToken(user);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testIsTokenExpired() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        String token = jwtService.getToken(user);

        // Create a spy instance of JwtService to mock specific methods
        JwtService spyJwtService = spy(jwtService);

        // Simulate expiration by setting an old date
        Date expirationDate = new Date(System.currentTimeMillis() - 1000 * 60 * 10); // 10 minutes ago
        doReturn(expirationDate).when(spyJwtService).getExpiration(token);

        boolean isExpired = spyJwtService.isTokenExpired(token);

        assertTrue(isExpired);
    }

    @Test
    void testGetClaim() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        String token = jwtService.getToken(user);
        String claim = jwtService.getClaim(token, Claims::getSubject);

        assertEquals("testuser", claim);
    }
}