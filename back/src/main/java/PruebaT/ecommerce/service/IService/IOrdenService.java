package PruebaT.ecommerce.service.IService;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.dto.OrdenDTO;
import PruebaT.ecommerce.security.model.User;

import java.awt.print.Pageable;
import java.util.List;

public interface IOrdenService {

    public List<OrdenDTO> listarOrden();
    public OrdenDTO buscarOrdenId(Integer id_orden);
    public OrdenDTO crearOrden(List<DetalleOrdenDTO> detalleOrden);
    void eliminarOrden(Integer id_orden);
    List<User> findTop5ByOrderByFrecuenciaDesc();
    List<Object[]> findTopProductosMasVendidos();
}
