package PruebaT.ecommerce.controller;

import PruebaT.ecommerce.dto.DescuentosDTO;
import PruebaT.ecommerce.model.Descuentos;
import PruebaT.ecommerce.service.implement.DescuentosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los descuentos de las órdenes.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@RestController
@RequestMapping("api")
@CrossOrigin(value = "http://localhost:4200")
public class DescuentosController {

    private static final Logger logger =
            LoggerFactory.getLogger(DescuentosController.class);

    @Autowired
    private DescuentosService descuentosService;

    /**
     * Listar todos los descuentos.
     *
     * @return una lista de objetos DescuentosDTO.
     */
    @GetMapping("/descuento")
    public List<DescuentosDTO> listarDescuentos() {
        return descuentosService.listarDescuentos();
    };

    /**
     * Guarda un nuevo descuento en el sistema.
     *
     * @param descuentos el objeto DescuentosDTO a guardar.
     * @return el objeto DescuentosDTO guardado con sus datos persistidos.
     */
    @PostMapping("/descuentos")
    public Descuentos guardarDescuentos(@RequestBody Descuentos descuentos) {
        var descuentoGuardado = this.descuentosService.guardarDescuento(descuentos);
        logger.info("Descuento guardadito" + descuentos);
        return descuentoGuardado;
    }

    /**
     * Filtra descuentos por estado.
     *
     * El campo estado de descuentos busca si esta activo o no dicho descuento.
     * @return una ResponseEntity que comprueba el estado del descuento a traves de una lista de Descuentos.
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Descuentos>> obtenerDescuentosActivos() {
        List<Descuentos> descuentosActivos = descuentosService.obtenerDescuentosActivos();
        return ResponseEntity.ok(descuentosActivos);
    }
}
