package PruebaT.ecommerce.service.implement;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.DetalleOrdenesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetalleOrdenesServiceTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private DetalleOrdenesRepository mockDetalleOrdenesRepository;

    @InjectMocks
    private DetalleOrdenService detalleOrdenServiceUnderTest;

    @Test
    void testListarDetalleOrdenIdOrden() {

        final DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setId(0);
        final Productos productos = new Productos();
        productos.setId(0);
        productos.setNombre("nombre");
        productos.setDescripcion("descripcion");
        detalleOrden.setProducto(productos);
        final List<DetalleOrden> detalleOrdens = List.of(detalleOrden);
        when(mockDetalleOrdenesRepository.findByOrdenId(0)).thenReturn(detalleOrdens);

        final DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        detalleOrdenDTO.setId(0);
        final Productos productos1 = new Productos();
        productos1.setId(0);
        productos1.setNombre("nombre");
        productos1.setDescripcion("descripcion");
        detalleOrdenDTO.setProducto(productos1);
        final DetalleOrden source = new DetalleOrden();
        source.setId(0);
        final Productos productos2 = new Productos();
        productos2.setId(0);
        productos2.setNombre("nombre");
        productos2.setDescripcion("descripcion");
        source.setProducto(productos2);
        when(mockModelMapper.map(source, DetalleOrdenDTO.class)).thenReturn(detalleOrdenDTO);

        final List<DetalleOrdenDTO> result = detalleOrdenServiceUnderTest.listarDetalleOrdenIdOrden(0);

    }

    @Test
    void testListarDetalleOrdenIdOrden_DetalleOrdenRepositoryReturnsNoItems() {
        when(mockDetalleOrdenesRepository.findByOrdenId(0)).thenReturn(Collections.emptyList());

        final List<DetalleOrdenDTO> result = detalleOrdenServiceUnderTest.listarDetalleOrdenIdOrden(0);
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
