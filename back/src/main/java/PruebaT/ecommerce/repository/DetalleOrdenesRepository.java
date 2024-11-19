package PruebaT.ecommerce.repository;

import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Ordenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad {@link Ordenes}.
 * Proporciona métodos para realizar consultas personalizadas sobre la tabla de ordenes.
 *
 * Extiende de {@link JpaRepository} que provee métodos básicos como guardar, eliminar y buscar.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Repository
public interface DetalleOrdenesRepository extends JpaRepository<DetalleOrden, Integer> {

    /**
     * Busca los detalles de la orden cuyo ID coincide con el especificado.
     *
     * @param ordenId el ID de la orden cuyos detalles se desean buscar.
     * @return una lista de detalles de la orden que coinciden con el ID especificado.
     */
    @Query("SELECT do FROM DetalleOrden do WHERE do.orden.id = :ordenId")
    List<DetalleOrden> findByOrdenId(Integer ordenId);

    @Query("SELECT p.nombre AS productoNombre, COUNT(d.producto.id) AS totalVendido " +
            "FROM DetalleOrden d " +
            "JOIN d.producto p " +
            "GROUP BY p.nombre " +
            "ORDER BY totalVendido DESC")
    List<Object[]> findTopProductosMasVendidos();

}
