package PruebaT.ecommerce.service.IService;

import PruebaT.ecommerce.dto.DescuentosDTO;
import PruebaT.ecommerce.model.Descuentos;

import java.util.List;

public interface IDescuentosService {

    public List<DescuentosDTO> listarDescuentos();
    public Descuentos guardarDescuento(Descuentos descuentos);
    public List<Descuentos> obtenerDescuentosActivos();
}
