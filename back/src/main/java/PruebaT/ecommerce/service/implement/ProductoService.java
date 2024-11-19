package PruebaT.ecommerce.service.implement;

import PruebaT.ecommerce.dto.ProductosDTO;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.ProductosRepository;
import PruebaT.ecommerce.service.IService.IOrdenService;
import PruebaT.ecommerce.service.IService.IProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los productos.
 * Implementa la interfaz {@link IProductoService} para proporcionar la lógica de negocio.
 *
 * Utiliza {@link ModelMapper} para convertir entre entidades y DTOs.
 * Utiliza {@link ProductosRepository} para acceder a la informacion y ajsutes del repositorio.
 * @author Roberto Cerquera
 * @version 1.0
 */
@Service
public class ProductoService implements IProductoService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductosRepository productosRepository;

    /**
     * Lista todos los productos.
     *
     * @return una lista de objetos ProductosDTO.
     */
    @Override
    public List<ProductosDTO> listarProductos() {
       var productos = this.productosRepository.findAll();
       return productos.stream()
               .map(producto -> this.modelMapper.map(producto, ProductosDTO.class))
               .collect(Collectors.toList());
    }


    /**
     * Busca productos por nombre, ignorando mayúsculas y minúsculas.
     *
     * @param producto el nombre del producto a buscar.
     * @return una lista de objetos ProductosDTO que contienen el nombre buscado.
     */
    @Override
    public List<ProductosDTO> findByNombreContainingIgnoreCase(String producto) {
        List<Productos> productos = this.productosRepository.findByNombreContainingIgnoreCase(producto);
       return productos.stream()
               .map(productoEntity -> this.modelMapper.map(productoEntity, ProductosDTO.class))
               .collect(Collectors.toList());
    }

    /**
     * Actualiza un producto existente.
     *
     * @param producto el objeto ProductosDTO con la información actualizada.
     * @return el objeto ProductosDTO actualizado.
     * @throws RecursoNoEncontradoException si el producto no es encontrado.
     */
    @Override
    public ProductosDTO actualizarProducto(ProductosDTO producto) {
        var productoEncontrado = this.productosRepository.findById(producto.getId())
                .orElseThrow(()-> new RecursoNoEncontradoException("producto no encontrado"));
        productoEncontrado.setNombre(producto.getNombre());
        productoEncontrado.setDescripcion(producto.getDescripcion());
        productoEncontrado.setStock(producto.getStock());
        productoEncontrado.setPrecio(producto.getPrecio());
        productoEncontrado.setImagen(producto.getImagen());

        var productoActualizado = productosRepository.save(productoEncontrado);
        return this.modelMapper.map(productoActualizado , ProductosDTO.class);
    }

    /**
     * Guarda un nuevo producto.
     *
     * @param producto el objeto ProductosDTO a guardar.
     * @return el objeto ProductosDTO guardado.
     */
    @Override
    public ProductosDTO guardarProducto(ProductosDTO producto) {
        var productoGuardado = this.productosRepository.save(modelMapper.map(producto, Productos.class));
        return modelMapper.map(productoGuardado, ProductosDTO.class);

    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id_producto el ID del producto a eliminar.
     * @throws RecursoNoEncontradoException si el producto no es encontrado.
     */
    @Override
    public void eliminarProducto(Integer id_producto) {
        if (!productosRepository.existsById(id_producto)) {
            throw new RecursoNoEncontradoException("Producto no encontrado");
        }
        productosRepository.deleteById(id_producto);
    }

    /**
     * Lista todos los productos por categoría.
     *
     * @param categoria la categoria para listar los productos asociados a ella.
     * @return una lista de productos segun por categoría.
     */
    @Override
    public List<Productos> findByCategoriaContainingIgnoreCase(String categoria) {
        return productosRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    /**
     * Lista todos los productos por precio mínimo y máximo.
     *
     * @param precioMin el precio mínimo del producto.
     * @param precioMax el precio máximo del producto.
     * @return una lista de productos relacionados en el precio mínimo y máximo.
     */
    @Override
    public List<Productos> findByPrecioBetween(Double precioMin, Double precioMax) {
        return productosRepository.findByPrecioBetween(precioMin, precioMax);
    }

    @Override
    public Long getTotalActiveProducts() {
        return productosRepository.countActiveProducts();
    }



}
