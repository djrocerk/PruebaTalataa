package PruebaT.ecommerce.repository;

import PruebaT.ecommerce.model.Descuentos;
import PruebaT.ecommerce.model.Ordenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para gestionar ciertas operaciones CRUD de la entidad {@link Descuentos}.
 * Realiza la operacion Read(Leer) para consultar y listar sobre la tabla de descuentos.
 *
 * Extiende de {@link JpaRepository} que provee métodos básicos como leer.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Repository
public interface DescuentosRepository  extends JpaRepository<Descuentos, Integer> {

    /**
     * Busca los descuentos de la fecha inicio y fecha fin.
     *
     * @param fecha la fecha de descuentos que se desea buscar.
     * @return una lista de fecha inicio y fecha fin segun el estado(si está activo o no).
     */
    List<Descuentos> findByFechaFinBeforeAndEstadoTrue(LocalDate fecha);
    List<Descuentos> findByFechaFinAfterAndEstadoTrue(LocalDate fecha);
}
