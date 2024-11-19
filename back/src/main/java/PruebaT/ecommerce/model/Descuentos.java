package PruebaT.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Representa los descuentos en el sistema de ecommerce.
 * Contiene la información de crear un descuento, en una fecha de inicio,
 * como tambien, en una fecha de fin. Se evalua si existe un descuento activo
 * o no.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name ="descuentos")
public class Descuentos extends Auditoria {


    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean estado = true;

}
