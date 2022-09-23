package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Especialidad.
 */

@Slf4j
@Service
public class EspecialidadService {

	@Autowired
	EspecialidadRepository especialidadRepository;
	
	/**
	 * Se encarga de crear una Especialidad en la base de datos.
	 *
	 * @param especialidad Objeto de EspecialidadEntity con los datos nuevos
	 * @return Objeto de EspecialidadEntity con los datos nuevos y su ID.
	 * @throws IllegalOperationException 
	 */
	@Transactional
	public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws IllegalOperationException {
		log.info("Inicia proceso de creación de la especialidad");
		if(especialidad.getDescripcion().length() < 10) {
			throw new IllegalOperationException("La descripcion no puede ser menor a 10 caracteres.");
	    }
		
		return especialidadRepository.save(especialidad);
	}

}