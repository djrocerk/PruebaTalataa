package PruebaT.ecommerce.controller;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.dto.OrdenDTO;
import PruebaT.ecommerce.security.model.User;
import PruebaT.ecommerce.service.implement.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


/**
 * Controlador REST para la gestión de órdenes.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@RestController
@RequestMapping("api")
@CrossOrigin(value = "http://localhost:4200")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    /**
     * Lista todas las órdenes.
     *
     * @return una lista de objetos OrdenDTO.
     */
    @GetMapping("/orden")
    public List<OrdenDTO> listarOrdenes() {
        return ordenService.listarOrden();
    }

    /**
     * Obtiene una orden por su ID.
     *
     * @param id el ID de la orden.
     * @return un ResponseEntity con el objeto OrdenDTO correspondiente.
     */
    @GetMapping("/orden/{id}")
    public ResponseEntity<OrdenDTO> obtenerOrdenPorId(@PathVariable int id) {
        OrdenDTO orden = ordenService.buscarOrdenId(id);
        return ResponseEntity.ok(orden);
    }

    /**
     * Crea una nueva orden.
     *
     * @param detallesOrden una lista de objetos DetalleOrdenDTO que representan los detalles de la orden.
     * @return un ResponseEntity con el objeto OrdenDTO creado.
     */
    @PostMapping("/orden")
    public ResponseEntity<?> crearOrden(@RequestBody List<DetalleOrdenDTO> detallesOrden) {
        try {
            OrdenDTO nuevaOrden = ordenService.crearOrden(detallesOrden);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    /**
     * Elimina una orden por su ID.
     *
     * @param id el ID de la orden a eliminar.
     * @return un ResponseEntity sin contenido.
     */
    @DeleteMapping("/orden/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable int id) {
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top-frecuentes")
    public List<User> obtenerTop5ClientesPorFrecuencia() {
        return ordenService.findTop5ByOrderByFrecuenciaDesc();
    }

    @GetMapping("/top-vendidos")
    public List<Object[]> findTopProductosMasVendidos() {
        return ordenService.findTopProductosMasVendidos();
    }

}
