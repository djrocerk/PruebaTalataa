package PruebaT.ecommerce.service.implement;

import PruebaT.ecommerce.dto.ProductosDTO;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.ProductosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private ProductosRepository mockProductosRepository;

    @InjectMocks
    private ProductoService productoServiceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lenient().when(mockProductosRepository.findById(0)).thenReturn(Optional.empty());

    }

    @Test
    void testListarProducto() {
        final Productos productos1 = new Productos();
        productos1.setNombre("nombre");
        productos1.setDescripcion("descripcion");
        productos1.setImagen("imagen");
        productos1.setStock(0);
        productos1.setPrecio(0.0);
        final List<Productos> productos = List.of(productos1);
        when(mockProductosRepository.findAll()).thenReturn(productos);


        final ProductosDTO productosDTO = new ProductosDTO();
        productosDTO.setId(0);
        productosDTO.setNombre("nombre");
        productosDTO.setDescripcion("descripcion");
        productosDTO.setImagen("imagen");
        productosDTO.setStock(0);
        productosDTO.setPrecio(0.0);
        final Productos source = new Productos();
        source.setNombre("nombre");
        source.setDescripcion("descripcion");
        source.setImagen("imagen");
        source.setStock(0);
        source.setPrecio(0.0);
        when(mockModelMapper.map(source, ProductosDTO.class)).thenReturn(productosDTO);

        final List<ProductosDTO> result = productoServiceUnderTest.listarProductos();

    }

    @Test
    void testListarProducto_ProductosRepositoryReturnsNoItems() {

        when(mockProductosRepository.findAll()).thenReturn(Collections.emptyList());
        final List<ProductosDTO> result = productoServiceUnderTest.listarProductos();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindByNombreContainingIgnoreCase() {
        final Productos productos1 = new Productos();
        productos1.setNombre("nombre");
        productos1.setDescripcion("descripcion");
        productos1.setImagen("imagen");
        productos1.setStock(0);
        productos1.setPrecio(0.0);

        final List<Productos> productosList = Arrays.asList(productos1);

        lenient().when(mockProductosRepository.findByNombreContainingIgnoreCase("Lapiz")).thenReturn(productosList);

        final ProductosDTO productosDTO = new ProductosDTO();
        productosDTO.setId(0);
        productosDTO.setNombre("nombre");
        productosDTO.setDescripcion("descripcion");
        productosDTO.setImagen("imagen");
        productosDTO.setStock(0);
        productosDTO.setPrecio(0.0);

        lenient().when(mockModelMapper.map(productos1, ProductosDTO.class)).thenReturn(productosDTO);

        final List<ProductosDTO> result = productoServiceUnderTest.findByNombreContainingIgnoreCase("Lapiz");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("nombre", result.get(0).getNombre());
    }

    @Test
    void testFindByNombreContainingIgnoreCase_ProductosRepositoryReturnsAbsent() {
        when(mockProductosRepository.findByNombreContainingIgnoreCase("Lapiz")).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> productoServiceUnderTest.findByNombreContainingIgnoreCase("Lapiz"))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void testActualizarProducto() {
        final ProductosDTO producto = new ProductosDTO();
        producto.setId(0);
        producto.setNombre("nombre");
        producto.setDescripcion("descripcion");
        producto.setImagen("imagen");
        producto.setStock(0);
        producto.setPrecio(0.0);

        final Productos productos1 = new Productos();
        productos1.setNombre("nombre");
        productos1.setDescripcion("descripcion");
        productos1.setImagen("imagen");
        productos1.setStock(0);
        productos1.setPrecio(0.0);
        final Optional<Productos> productos = Optional.of(productos1);
        when(mockProductosRepository.findById(0)).thenReturn(productos);

        final Productos productos2 = new Productos();
        productos2.setNombre("nombre");
        productos2.setDescripcion("descripcion");
        productos2.setImagen("imagen");
        productos2.setStock(0);
        productos2.setPrecio(0.0);
        final Productos entity = new Productos();
        entity.setNombre("nombre");
        entity.setDescripcion("descripcion");
        entity.setImagen("imagen");
        entity.setStock(0);
        entity.setPrecio(0.0);
        when(mockProductosRepository.save(entity)).thenReturn(productos2);

        final ProductosDTO productosDTO = new ProductosDTO();
        productosDTO.setId(0);
        productosDTO.setNombre("nombre");
        productosDTO.setDescripcion("descripcion");
        productosDTO.setImagen("imagen");
        productosDTO.setStock(0);
        productosDTO.setPrecio(0.0);
        final Productos source = new Productos();
        source.setNombre("nombre");
        source.setDescripcion("descripcion");
        source.setImagen("imagen");
        source.setStock(0);
        source.setPrecio(0.0);
        when(mockModelMapper.map(source, ProductosDTO.class)).thenReturn(productosDTO);

        final ProductosDTO result = productoServiceUnderTest.actualizarProducto(producto);
    }

    @Test
    void testActualizarProducto_ProductosRepositoryFindByIdReturnsAbsent() {
        final ProductosDTO producto = new ProductosDTO();
        producto.setId(0);
        producto.setNombre("nombre");
        producto.setDescripcion("descripcion");
        producto.setImagen("imagen");
        producto.setStock(0);
        producto.setPrecio(0.0);

        when(mockProductosRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoServiceUnderTest.actualizarProducto(producto))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void testGuardarProducto() {
        final ProductosDTO producto = new ProductosDTO();
        producto.setId(0);
        producto.setNombre("nombre");
        producto.setDescripcion("descripcion");
        producto.setImagen("imagen");
        producto.setStock(0);
        producto.setPrecio(0.0);

        final Productos productos = new Productos();
        productos.setNombre("nombre");
        productos.setDescripcion("descripcion");
        productos.setImagen("imagen");
        productos.setStock(0);
        productos.setPrecio(0.0);
        when(mockModelMapper.map(any(Object.class), eq(Productos.class))).thenReturn(productos);

        final Productos productos1 = new Productos();
        productos1.setNombre("nombre");
        productos1.setDescripcion("descripcion");
        productos1.setImagen("imagen");
        productos1.setStock(0);
        productos1.setPrecio(0.0);
        final Productos entity = new Productos();
        entity.setNombre("nombre");
        entity.setDescripcion("descripcion");
        entity.setImagen("imagen");
        entity.setStock(0);
        entity.setPrecio(0.0);
        when(mockProductosRepository.save(entity)).thenReturn(productos1);

        final ProductosDTO result = productoServiceUnderTest.guardarProducto(producto);

    }




}
