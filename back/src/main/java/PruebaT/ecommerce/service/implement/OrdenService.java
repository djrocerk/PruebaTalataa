package PruebaT.ecommerce.service.implement;


import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.dto.OrdenDTO;
import PruebaT.ecommerce.exception.ProductoNoEncontradoException;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.exception.StockInsuficienteException;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Ordenes;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.DetalleOrdenesRepository;
import PruebaT.ecommerce.repository.OrdenesRepository;
import PruebaT.ecommerce.repository.ProductosRepository;
import PruebaT.ecommerce.security.model.User;
import PruebaT.ecommerce.security.repository.UserRepository;
import PruebaT.ecommerce.service.IService.IDetalleOrdenService;
import PruebaT.ecommerce.service.IService.IOrdenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los de las órdenes.
 * Implementa la interfaz {@link IOrdenService} para proporcionar la lógica de negocio.
 *
 * Utiliza {@link ModelMapper} para convertir entre entidades y DTOs.
 * Utiliza {@link OrdenesRepository} para acceder a la informacion y ajsutes del repositorio.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Service
public class OrdenService implements IOrdenService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdenesRepository ordenesRepository;

    @Autowired
    private DetalleOrdenesRepository detalleOrdenesRepository;

    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * Lista todas las órdenes.
     *
     * @return una lista de objetos OrdenDTO.
     */
    @Override
    public List<OrdenDTO> listarOrden() {
        var ordenes = this.ordenesRepository.findAll();
        return ordenes.stream()
                .map(orden -> this.modelMapper.map(orden , OrdenDTO.class))
                .collect(Collectors.toList());
    }


    /**
     * Busca una orden por su ID.
     *
     * @param pedidoId el ID de la orden a buscar.
     * @return el objeto OrdenDTO encontrado.
     * @throws RecursoNoEncontradoException si la orden no es encontrada.
     */
    @Override
    public OrdenDTO buscarOrdenId(Integer pedidoId) {
        var orden =  this.ordenesRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("orden no encontrado"));
        return this.modelMapper.map(orden, OrdenDTO.class);
    }


    /**
     * Crea una nueva orden.
     *
     * @param detalleOrden una lista de objetos DetalleOrdenDTO que representan los detalles de la orden.
     * @return el objeto OrdenDTO creado.
     * @throws ProductoNoEncontradoException si un producto no es encontrado.
     * @throws StockInsuficienteException si no hay suficiente stock para un producto.
     * @throws ResponseStatusException si hay un error general.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrdenDTO crearOrden(List<DetalleOrdenDTO> detalleOrden) {
        try {
            double total = 0.0;
            for (DetalleOrdenDTO detalle : detalleOrden) {
                int productoId = detalle.getProducto().getId();
                Productos producto = productosRepository.findById(productoId)
                        .orElseThrow(() -> new ProductoNoEncontradoException("No existe el producto con ID: " + productoId));

                int cantidad = detalle.getCantidad();
                if (producto.getStock() < cantidad) {
                    throw new StockInsuficienteException("No hay suficiente stock para el producto: " + producto.getNombre());
                }

                double precioUnitario = producto.getPrecio();
                double subtotal = cantidad * precioUnitario;
                total += subtotal;

                producto.setStock(producto.getStock() - cantidad);
                productosRepository.save(producto);

                detalle.setPrecioUnidad(precioUnitario);
                detalle.setSubtotal(subtotal);
            }

            Integer userId = getUserIdFromToken();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

            if (user.getFrecuencia() == null) {
                user.setFrecuencia(1);
            } else {
                user.setFrecuencia(user.getFrecuencia() + 1);
            }
            userRepository.save(user);

            Ordenes ordenes = new Ordenes();
            ordenes.setFecha(new Date());
            ordenes.setTotal(total);
            ordenes.setUser(user);

            ordenes = ordenesRepository.save(ordenes);

            for (DetalleOrdenDTO detalleOrdenDTO : detalleOrden) {
                DetalleOrden detalleOrden1 = modelMapper.map(detalleOrdenDTO, DetalleOrden.class);
                detalleOrden1.setOrden(ordenes);
                detalleOrdenesRepository.save(detalleOrden1);
            }

            return modelMapper.map(ordenes, OrdenDTO.class);
        } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la orden: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminarOrden(Integer id_orden) {
        var orden = ordenesRepository.findById(id_orden)
                .orElseThrow(() -> new RecursoNoEncontradoException("Orden no encontrada"));

        for (DetalleOrden detalleOrden : orden.getDetalles()) {
            Productos producto = detalleOrden.getProducto();
            producto.setStock(producto.getStock() + detalleOrden.getCantidad());
            productosRepository.save(producto);
        }
        ordenesRepository.delete(orden);
    }

    @Override
    public List<User> findTop5ByOrderByFrecuenciaDesc() {
        return userRepository.findTop5ByOrderByFrecuenciaDesc();
    }

    @Override
    public List<Object[]> findTopProductosMasVendidos() {
        return detalleOrdenesRepository.findTopProductosMasVendidos();
    }


    public Integer getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return user.getId();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autorizado");
    }
}
