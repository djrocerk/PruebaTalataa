package PruebaT.ecommerce.controller;


import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.service.implement.DetalleOrdenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los detalles de las órdenes.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@RestController
@RequestMapping("api")
@CrossOrigin(value = "http://localhost:4200")
public class DetalleOrdenController {
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    /**
     * Obtiene los detalles de una orden por su ID.
     *
     * @param ordenId el ID de la orden.
     * @return un ResponseEntity con una lista de objetos DetalleOrdenDTO.
     */
    @GetMapping("/detalle/{ordenId}")
    public ResponseEntity<List<DetalleOrdenDTO>> getDetallesPorPedidoId(
            @ApiParam(value = "ID de la orden", required = true, example = "1")
            @PathVariable Integer ordenId) {
        List<DetalleOrdenDTO> detalles = detalleOrdenService.listarDetalleOrdenIdOrden(ordenId);
        return ResponseEntity.ok(detalles);

    }
}
