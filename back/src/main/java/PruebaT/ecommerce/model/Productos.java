package PruebaT.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa un producto en el sistema de ecommerce.
 * Contiene la información básica sobre un producto, incluyendo nombre,
 * descripción, imagen, stock, categoria y precio.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "productos")
public class Productos extends Auditoria {

    public enum categoria {
        Electronicos,
        Alimentos,
        Deportivos
    }


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precio")
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private categoria categoria;

}
