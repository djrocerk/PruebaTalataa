package PruebaT.ecommerce.security.controller;


import PruebaT.ecommerce.security.dto.*;
import PruebaT.ecommerce.security.model.User;
import PruebaT.ecommerce.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controlador para manejar solicitudes relacionadas con la autenticación.
 * Proporciona endpoints para el inicio de sesión y el registro de usuarios.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@RestController
@RequestMapping("api/security")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para el inicio de sesión de usuarios.
     *
     * @param request la solicitud de inicio de sesión que contiene las credenciales del usuario
     * @return una entidad de respuesta que contiene la respuesta de autenticación
     */
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Endpoint para el registro de usuarios.
     *
     * @param request la solicitud de registro que contiene los detalles del usuario
     * @return una entidad de respuesta que contiene la respuesta de autenticación
     */
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping(value = "consultar-usuarios")
    public List<UserDto> consultUsers(){
        return authService.consultUsers();
    }

    /**
     * Endpoint para manejar los datos de un usuario y un ID.
     *
     * @param id   el ID asociado a la operación
     * @param user los datos del usuario en el cuerpo de la solicitud
     * @return una respuesta indicando el resultado de la operación
     */
    @PutMapping(value = "/modificar-usuario/{id}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable Integer id, @RequestBody User user) {
        authService.update(id, user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario procesado exitosamente");
        response.put("id", String.valueOf(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para manejar el borrado lógico de un usuario.
     *
     * @param id el ID del usuario a eliminar
     * @return una respuesta indicando el resultado de la operación
     */
    @PutMapping(value = "/eliminar-usuario/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Integer id) {
        authService.delete(id); // Llamar al servicio para realizar el borrado lógico
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario eliminado exitosamente");
        response.put("id", String.valueOf(id));
        return ResponseEntity.ok(response);
    }

}
