package PruebaT.ecommerce.repository;

import PruebaT.ecommerce.model.Ordenes;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
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
public interface OrdenesRepository extends JpaRepository<Ordenes, Integer> {

}
