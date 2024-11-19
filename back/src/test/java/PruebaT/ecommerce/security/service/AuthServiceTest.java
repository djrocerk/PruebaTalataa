package PruebaT.ecommerce.security.service;

import PruebaT.ecommerce.security.dto.AuthResponse;
import PruebaT.ecommerce.security.dto.LoginRequest;
import PruebaT.ecommerce.security.dto.RegisterRequest;
import PruebaT.ecommerce.security.model.User;
import PruebaT.ecommerce.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private JwtService mockJwtService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private AuthenticationManager mockAuthenticationManager;

    @InjectMocks
    private AuthService authServiceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        User user = User.builder()
                .username("username")
                .password("encodedPassword")
                .firstname("first")
                .lastname("last")
                .email("email@example.com")
                .build();
        UserDetails userDetails = (UserDetails) user;

        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(mockJwtService.getToken(any(User.class))).thenReturn("jwtToken");
        when(mockAuthenticationManager.authenticate(any())).thenReturn(null);

        AuthResponse response = authServiceUnderTest.login(loginRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwtToken");

        verify(mockUserRepository).findByUsername("username");
        verify(mockJwtService).getToken(user);
        verify(mockAuthenticationManager).authenticate(any());
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequest loginRequest = new LoginRequest("username", "password");

        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(mockAuthenticationManager.authenticate(any())).thenReturn(null); // O devuelve un objeto adecuado

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authServiceUnderTest.login(loginRequest));
        assertThat(exception.getMessage()).contains("No se encontró el usuario");

        verify(mockUserRepository).findByUsername("username");
        verify(mockAuthenticationManager).authenticate(any());
    }

    @Test
    void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "first", "last", "email@example.com");
        User user = User.builder()
                .username("username")
                .password("encodedPassword")
                .firstname("first")
                .lastname("last")
                .email("email@example.com")
                .build();

        when(mockPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(mockJwtService.getToken(any(User.class))).thenReturn("jwtToken");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        AuthResponse response = authServiceUnderTest.register(registerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwtToken");

        verify(mockPasswordEncoder).encode("password");
        verify(mockUserRepository).save(user);
        verify(mockJwtService).getToken(user);
    }
}
