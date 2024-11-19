package PruebaT.ecommerce.model;

import PruebaT.ecommerce.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * Representa una orden de compra en el sistema de ecommerce.
 * Contiene información relacionada con la fecha de la orden,
 * el total de la compra, y los detalles de la orden.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "ordenes")
public class Ordenes extends Auditoria {


    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("orden")
    private List<DetalleOrden> detalles;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
