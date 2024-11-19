package PruebaT.ecommerce.security.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
}
