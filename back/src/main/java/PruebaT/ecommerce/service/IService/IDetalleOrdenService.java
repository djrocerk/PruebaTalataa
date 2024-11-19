package PruebaT.ecommerce.service.IService;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;

import java.util.List;

public interface IDetalleOrdenService {
    public List<DetalleOrdenDTO> listarDetalleOrdenIdOrden(Integer pedidoId);
}
