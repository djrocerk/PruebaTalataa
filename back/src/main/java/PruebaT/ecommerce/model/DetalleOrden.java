package PruebaT.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * Representa el detalle de una orden en el sistema de ecommerce.
 * Cada detalle de la orden está relacionado con un producto específico,
 * y contiene información como la cantidad comprada, el precio por unidad
 * y el subtotal.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Table(name = "detalle_pedidos")
public class DetalleOrden extends Auditoria {


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos producto;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Ordenes orden;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unidad")
    private Double precioUnidad;

    @Column(name = "subtotal")
    private Double subtotal;

}
