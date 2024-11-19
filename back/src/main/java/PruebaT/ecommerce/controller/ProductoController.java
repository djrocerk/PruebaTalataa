package PruebaT.ecommerce.controller;

import PruebaT.ecommerce.dto.ProductosDTO;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.service.implement.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 *
 * Este controlador expone endpoints para realizar operaciones CRUD sobre productos,
 * incluyendo creación, lectura, actualización y eliminación.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@RestController
@RequestMapping("api")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoController {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    /**
     * Lista todos los productos almacenados en el sistema.
     *
     * @return una lista de objetos ProductosDTO.
     */
    @GetMapping("/producto")
    public List<ProductosDTO> listarProducto() {
        return this.productoService.listarProductos();
    }

    /**
     * Busca productos por nombre, ignorando diferencias entre mayúsculas y minúsculas.
     *
     * @param nombre el nombre del producto a buscar.
     * @return una ResponseEntity que contiene una lista de ProductosDTO con nombres coincidentes.
     */
    @GetMapping("/producto/{nombre}")
    public ResponseEntity<List<ProductosDTO>> obtenerProductoPorNombre(@PathVariable String nombre) {
        List<ProductosDTO> productosDTO = this.productoService.findByNombreContainingIgnoreCase(nombre);
        return ResponseEntity.ok(productosDTO);
    }

    /**
     * Crea un nuevo producto en el sistema.
     *
     * @param producto el objeto ProductosDTO a agregar.
     * @return el objeto ProductosDTO agregado con sus datos persistidos.
     */
    @PostMapping("/producto")
    public ProductosDTO agregarProducto(@RequestBody ProductosDTO producto) {

        logger.info("JSON recibido en el controlador: {}", producto);

        var productoGuardado = this.productoService.guardarProducto(producto);


        logger.info("Producto guardado en la base de datos: {}", productoGuardado);

        return productoGuardado;
    }

    /**
     * Actualiza un producto existente en el sistema.
     *
     * @param producto el objeto ProductosDTO con los datos actualizados.
     * @return una ResponseEntity que contiene el objeto ProductosDTO actualizado.
     */
    @PutMapping("/producto")
    public ResponseEntity<ProductosDTO> actualizarProducto(@RequestBody ProductosDTO producto) {

        logger.info("JSON recibido para actualización: {}", producto);

        ProductosDTO productoActualizado = productoService.actualizarProducto(producto);


        logger.info("Producto actualizado en la base de datos: {}", productoActualizado);

        return ResponseEntity.ok(productoActualizado);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar.
     * @return una ResponseEntity sin contenido.
     */
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        logger.info("Eliminando producto con ID: {}", id);

        productoService.eliminarProducto(id);

        logger.info("Producto con ID {} eliminado exitosamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Filtra productos por categoría.
     *
     * @param categoria la categoría del producto a buscar.
     * @return una ResponseEntity que contiene una lista de Productos con categorías coincidentes.
     */
    @GetMapping("/producto/categoria/{categoria}")
    public ResponseEntity<List<Productos>> obtenerProductoPorCategoria(@PathVariable String categoria) {
        List<Productos> productos = this.productoService.findByCategoriaContainingIgnoreCase(categoria);
        return ResponseEntity.ok(productos);
    }

    /**
     * Filtra productos por rango de precios.
     *
     * @param min el precio mínimo.
     * @param max el precio máximo.
     * @return una ResponseEntity que contiene una lista de Productos dentro del rango de precios especificado.
     */
    @GetMapping("/producto/precio")
    public ResponseEntity<List<Productos>> obtenerProductoPorRangoDePrecio(
            @RequestParam("min") Double min,
            @RequestParam("max") Double max) {
        List<Productos> productos = this.productoService.findByPrecioBetween(min, max);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/producto/estado/true/count")
    public ResponseEntity<Long> obtenerTotalProductosActivos() {
        Long totalActivos = productoService.getTotalActiveProducts();
        return ResponseEntity.ok(totalActivos);
    }

}
