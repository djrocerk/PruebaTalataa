package PruebaT.ecommerce.service.implement;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.repository.DetalleOrdenesRepository;
import PruebaT.ecommerce.service.IService.IDetalleOrdenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los detalles de las órdenes.
 * Implementa la interfaz {@link IDetalleOrdenService} para proporcionar la lógica de negocio.
 *
 * Utiliza {@link ModelMapper} para convertir entre entidades y DTOs.
 * Utiliza {@link DetalleOrdenesRepository} para acceder a la informacion y ajsutes del repositorio.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Service
public class DetalleOrdenService implements IDetalleOrdenService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DetalleOrdenesRepository detalleOrdenesRepository;

    /**
     * Lista los detalle de la orden cuyo ID coincide con el especificado.
     *
     * @param pedidoId el ID de la orden cuyos detalles se desean listar.
     * @return una lista de {@link DetalleOrdenDTO} que coinciden con el ID especificado.
     */
    @Override
    public List<DetalleOrdenDTO> listarDetalleOrdenIdOrden(Integer pedidoId) {
        List<DetalleOrden> detalleOrden = detalleOrdenesRepository.findByOrdenId(pedidoId);
        return detalleOrden.stream()
                .map(detalle -> modelMapper.map(detalle, DetalleOrdenDTO.class))
                .collect(Collectors.toList());
    }
}
