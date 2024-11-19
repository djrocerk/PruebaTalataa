package PruebaT.ecommerce.security.dto;

import PruebaT.ecommerce.security.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    String token;
    Role role;
    Integer frecuencia;
}
