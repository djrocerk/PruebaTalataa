package PruebaT.ecommerce.dto;

import PruebaT.ecommerce.model.Productos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductosDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Integer stock;
    private Double precio;
    private Productos.categoria categoria;
}
