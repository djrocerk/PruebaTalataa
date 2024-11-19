package PruebaT.ecommerce.security.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JwtAuthTest {
    @Mock
    private JwtService mockJwtService;

    @Mock
    private UserDetailsService mockUserDetailsService;

    @InjectMocks
    private JwtAuth jwtAuth;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private FilterChain mockFilterChain;

    @Mock
    private UserDetails mockUserDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "validToken";
        String username = "username";

        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(mockJwtService.getUsernameFromToken(token)).thenReturn(username);
        when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        when(mockJwtService.isTokenValid(token, mockUserDetails)).thenReturn(true);

        jwtAuth.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        verify(mockJwtService).getUsernameFromToken(token);
        verify(mockUserDetailsService).loadUserByUsername(username);
        verify(mockJwtService).isTokenValid(token, mockUserDetails);
        verify(mockFilterChain).doFilter(mockRequest, mockResponse);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        String token = "invalidToken";
        String username = "username";

        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(mockJwtService.getUsernameFromToken(token)).thenReturn(username);
        when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        when(mockJwtService.isTokenValid(token, mockUserDetails)).thenReturn(false);

        jwtAuth.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        verify(mockJwtService).getUsernameFromToken(token);
        verify(mockUserDetailsService).loadUserByUsername(username);
        verify(mockJwtService).isTokenValid(token, mockUserDetails);
        verify(mockFilterChain).doFilter(mockRequest, mockResponse);
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        jwtAuth.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        verify(mockFilterChain).doFilter(mockRequest, mockResponse);
    }
}