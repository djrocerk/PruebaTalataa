package PruebaT.ecommerce.service.implement;

import PruebaT.ecommerce.dto.DescuentosDTO;
import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.model.Descuentos;
import PruebaT.ecommerce.repository.DescuentosRepository;
import PruebaT.ecommerce.repository.DetalleOrdenesRepository;
import PruebaT.ecommerce.service.IService.IDescuentosService;
import PruebaT.ecommerce.service.IService.IDetalleOrdenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los descuentos.
 * Implementa la interfaz {@link IDescuentosService} para proporcionar la lógica de negocio.
 *
 * Utiliza {@link ModelMapper} para convertir entre entidades y DTOs.
 * Utiliza {@link DescuentosRepository} para acceder a la informacion y ajsutes del repositorio.
 *
 * @author Roberto Cerquera
 * @version 1.0
 */
@Service
public class DescuentosService implements IDescuentosService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DescuentosRepository descuentosRepository;

    /**
     * Lista los descuentos.
     *
     *
     * @return una lista de {@link DescuentosDTO}.
     */
    @Override
    public List<DescuentosDTO> listarDescuentos() {
        var descuentos = this.descuentosRepository.findAll();
        return descuentos.stream()
                .map(descuent -> this.modelMapper.map(descuent, DescuentosDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo descuento en el sistema.
     *
     * @param descuentos el objeto DescuentosDTO a guardar.
     * @return el objeto DescuentosDTO guardado.
     */
    @Override
    public Descuentos guardarDescuento(Descuentos descuentos) {
        return descuentosRepository.save(modelMapper.map(descuentos, Descuentos.class));
    }

    /**
     * Obtiene los descuentos por estado.
     *
     * Se lista el descuento y verifica si está activo, por medio de la fecha.
     * @return la fecha fin para evaluar el estado del descuento.
     */
    @Override
    public List<Descuentos> obtenerDescuentosActivos() {
        LocalDate fechaActual = LocalDate.now();
        return descuentosRepository.findByFechaFinAfterAndEstadoTrue(fechaActual);
    }

    /**
     * Verifica el estado del descuento por un periodo de tiempo.
     *
     * Se lista el descuento y verifica si está activo, por medio de la fecha.
     * Se evalua el estado del descuento y se guarda.
     *
     * Se hace pruebas, para corroborar la expiración del descuento.
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void verificarDescuentosExpirados() {

        LocalDate fechaActual = LocalDate.now();

        List<Descuentos> descuentosExpirados = descuentosRepository.findByFechaFinBeforeAndEstadoTrue(fechaActual);

        for (Descuentos descuento : descuentosExpirados) {
            descuento.setEstado(false);
            descuentosRepository.save(descuento);
        }

        System.out.println("Descuentos expirados actualizados.");
    }
}
