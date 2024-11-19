package PruebaT.ecommerce.security.service;


import PruebaT.ecommerce.repository.OrdenesRepository;
import PruebaT.ecommerce.security.dto.*;
import PruebaT.ecommerce.security.model.User;
import PruebaT.ecommerce.security.repository.UserRepository;
import PruebaT.ecommerce.service.IService.IOrdenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Servicio para la autenticación y registro de usuarios.
 *
 * Utiliza {@link UserRepository} para acceder a la informacion y ajsutes del repositorio.
 * Utiliza {@link JwtService} para acceder a los servicios de JWT.
 * Utiliza {@link PasswordEncoder} para acceder a los servicios de encriptacion de comparacion de contraseña.
 * Utiliza {@link AuthenticationManager} para acceder a los servicios de manejo de sesion de spring security.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Autentica a un usuario y genera un token JWT.
     *
     * @param request el objeto LoginRequest que contiene las credenciales del usuario.
     * @return un AuthResponse con el token JWT.
     */
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario"));

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .role(user.getRole())
                .frecuencia(user.getFrecuencia())
                .build();
    }

    /**
     * Registra a un nuevo usuario y genera un token JWT.
     *
     * @param request el objeto RegisterRequest que contiene los datos del nuevo usuario.
     * @return un AuthResponse con el token JWT.
     */
    public AuthResponse register(RegisterRequest request){
        User user=User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .frecuencia(request.getFrecuencia())
                .build();

        userRepository.save(user);
        return AuthResponse.builder().token(jwtService.getToken(user)).
                role(user.getRole()).build();
    }

    public List<UserDto> consultUsers(){
        return userRepository.consultUsers();
    }

    /**
     * Actualiza un usuario existente basado en el ID proporcionado.
     *
     * @param id   el ID del usuario a actualizar
     * @param user los datos actualizados del usuario
     */
    public void update(Integer id, User user) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        User existingUser = existingUserOpt.get();

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setFrecuencia(user.getFrecuencia());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);
    }

    /**
     * Realiza el borrado lógico de un usuario.
     *
     * @param id el ID del usuario a eliminar
     */
    public void delete(Integer id) {

        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        User existingUser = existingUserOpt.get();
        existingUser.setEstado(false);

        userRepository.save(existingUser);
    }
}
