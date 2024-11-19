package PruebaT.ecommerce.service.IService;

import PruebaT.ecommerce.dto.ProductosDTO;
import PruebaT.ecommerce.model.Productos;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoService {

    public List<ProductosDTO> listarProductos();
    public List<ProductosDTO> findByNombreContainingIgnoreCase(String producto);
    public ProductosDTO actualizarProducto(ProductosDTO producto);
    public ProductosDTO guardarProducto(ProductosDTO producto);
    void eliminarProducto(Integer id_producto);
    List<Productos> findByCategoriaContainingIgnoreCase(String categoria);
    List<Productos> findByPrecioBetween(Double precioMin,Double precioMax);
    Long getTotalActiveProducts();
}
