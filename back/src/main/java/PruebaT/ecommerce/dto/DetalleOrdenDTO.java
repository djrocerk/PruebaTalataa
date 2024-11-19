package PruebaT.ecommerce.dto;

import PruebaT.ecommerce.model.Ordenes;
import PruebaT.ecommerce.model.Productos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleOrdenDTO {
    private Integer id;
    private Productos producto;
    private Ordenes orden;
    private Integer cantidad;
    private Double precioUnidad;
    private Double subtotal;
}
