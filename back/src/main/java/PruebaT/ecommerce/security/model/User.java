package PruebaT.ecommerce.security.model;

import PruebaT.ecommerce.model.Auditoria;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Entidad que representa a un usuario en el sistema de seguridad.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "users")
public class User extends Auditoria implements UserDetails {


    String username;
    @Column(name = "password")
    String password;
    @Column(name = "firstname")
    String firstname;
    @Column(name = "lastname")
    String lastname;
    @Column(name = "email")
    String email;
    @Column(name = "frecuencia")
    Integer frecuencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    /**
     * Obtiene las autoridades concedidas al usuario.
     *
     * @return una colección vacía ya que no se han definido roles.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     *
     * @return true siempre, ya que no se maneja expiración de cuentas.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * Indica si la cuenta del usuario está bloqueada.
     *
     * @return true siempre, ya que no se maneja bloqueo de cuentas.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario han expirado.
     *
     * @return true siempre, ya que no se maneja expiración de credenciales.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está habilitada.
     *
     * @return true siempre, ya que todas las cuentas están habilitadas por defecto.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
