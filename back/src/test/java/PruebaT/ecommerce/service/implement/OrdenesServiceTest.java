package PruebaT.ecommerce.service.implement;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.dto.OrdenDTO;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Ordenes;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.DetalleOrdenesRepository;
import PruebaT.ecommerce.repository.OrdenesRepository;
import PruebaT.ecommerce.repository.ProductosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdenesServiceTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private OrdenesRepository mockOrdenesRepository;
    @Mock
    private DetalleOrdenesRepository mockDetalleOrdenesRepository;
    @Mock
    private ProductosRepository mockProductosRepository;

    @InjectMocks
    private OrdenService ordenServiceUnderTest;

    @Test
    void testListarOrden() {

        final Ordenes ordenes = new Ordenes();
        ordenes.setId(0);
        ordenes.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        ordenes.setTotal(0.0);
        final DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setId(0);
        ordenes.setDetalles(List.of(detalleOrden));
        final List<Ordenes> Ordenes = List.of(ordenes);
        when(mockOrdenesRepository.findAll()).thenReturn(Ordenes);

        final OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setId(0);
        ordenDTO.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        ordenDTO.setTotal(0.0);
        final Ordenes source = new Ordenes();
        source.setId(0);
        source.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        source.setTotal(0.0);
        final DetalleOrden detalleOrden1 = new DetalleOrden();
        detalleOrden1.setId(0);
        source.setDetalles(List.of(detalleOrden1));
        when(mockModelMapper.map(source, OrdenDTO.class)).thenReturn(ordenDTO);

        final List<OrdenDTO> result = ordenServiceUnderTest.listarOrden();

    }

    @Test
    void testListarOrden_OrdenRepositoryReturnsNoItems() {
        when(mockOrdenesRepository.findAll()).thenReturn(Collections.emptyList());

        final List<OrdenDTO> result = ordenServiceUnderTest.listarOrden();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testBuscarOrdenId() {
        final Ordenes ordenes1 = new Ordenes();
        ordenes1.setId(0);
        ordenes1.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        ordenes1.setTotal(0.0);
        final DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setId(0);
        ordenes1.setDetalles(List.of(detalleOrden));
        final Optional<Ordenes> orden = Optional.of(ordenes1);
        when(mockOrdenesRepository.findById(0)).thenReturn(orden);

        final OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setId(0);
        ordenDTO.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        ordenDTO.setTotal(0.0);
        final Ordenes source = new Ordenes();
        source.setId(0);
        source.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        source.setTotal(0.0);
        final DetalleOrden detalleOrden1 = new DetalleOrden();
        detalleOrden1.setId(0);
        source.setDetalles(List.of(detalleOrden1));
        when(mockModelMapper.map(source, OrdenDTO.class)).thenReturn(ordenDTO);

        final OrdenDTO result = ordenServiceUnderTest.buscarOrdenId(0);
    }

    @Test
    void testBuscarOrdenId_OrdenRepositoryReturnsAbsent() {
        when(mockOrdenesRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordenServiceUnderTest.buscarOrdenId(0))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void testCrearOrden() {
        final DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        final Productos productos = new Productos();
        productos.setId(0);
        detalleOrdenDTO.setProducto(productos);
        detalleOrdenDTO.setCantidad(1);
        final List<DetalleOrdenDTO> detalleOrden = List.of(detalleOrdenDTO);

        final Productos productos2 = new Productos();
        productos2.setId(0);
        productos2.setNombre("nombre");
        productos2.setDescripcion("descripcion");
        productos2.setStock(10);
        productos2.setPrecio(100.0);
        final Optional<Productos> productos1 = Optional.of(productos2);
        when(mockProductosRepository.findById(0)).thenReturn(productos1);

        when(mockOrdenesRepository.save(any(Ordenes.class))).thenAnswer(invocation -> {
            Ordenes argument = invocation.getArgument(0);
            argument.setId(1);
            return argument;
        });

        when(mockModelMapper.map(any(Object.class), eq(DetalleOrden.class))).thenAnswer(invocation -> {
            DetalleOrdenDTO detalle = invocation.getArgument(0);
            DetalleOrden detalleOrden1 = new DetalleOrden();
            detalleOrden1.setId(0);
            detalleOrden1.setProducto(productos2);
            return detalleOrden1;
        });

        final OrdenDTO result = ordenServiceUnderTest.crearOrden(detalleOrden);

        verify(mockProductosRepository).save(productos2);

        final DetalleOrden entity2 = new DetalleOrden();
        entity2.setId(0);
        entity2.setProducto(productos2);

        ArgumentMatcher<DetalleOrden> detalleOrdenMatcher = new ArgumentMatcher<>() {
            @Override
            public boolean matches(DetalleOrden detalleOrden) {
                return detalleOrden.getId() == entity2.getId() &&
                        detalleOrden.getProducto().equals(entity2.getProducto()) &&
                        detalleOrden.getCantidad() == entity2.getCantidad() &&
                        detalleOrden.getPrecioUnidad() == entity2.getPrecioUnidad() &&
                        detalleOrden.getSubtotal() == entity2.getSubtotal();
            }
        };

        verify(mockDetalleOrdenesRepository).save(argThat(detalleOrdenMatcher));
    }

    @Test
    void testCrearOrden_ProductosRepositoryFindByIdReturnsAbsent() {
        final DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        final Productos productos = new Productos();
        productos.setId(0);
        productos.setStock(0);
        productos.setPrecio(0.0);
        detalleOrdenDTO.setProducto(productos);
        detalleOrdenDTO.setCantidad(0);
        detalleOrdenDTO.setPrecioUnidad(0.0);
        detalleOrdenDTO.setSubtotal(0.0);
        final List<DetalleOrdenDTO> detalleOrden = List.of(detalleOrdenDTO);
        when(mockProductosRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordenServiceUnderTest.crearOrden(detalleOrden))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void testEliminarPedido_OrdenRepositoryFindByIdReturnsAbsent() {
        when(mockOrdenesRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordenServiceUnderTest.eliminarOrden(0))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }
}
