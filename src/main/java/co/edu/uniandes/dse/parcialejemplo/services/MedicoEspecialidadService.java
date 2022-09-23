package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Medico y Especialidad.
 *
 * @medico ISIS2603
 */

@Slf4j
@Service
public class MedicoEspecialidadService {

	@Autowired
	private EspecialidadRepository especialidadRepository;

	@Autowired
	private MedicoRepository medicoRepository;
	
	/**
	 * Asocia una Especialidad existente a un Medico
	 *
	 * @param medicoId Identificador de la instancia de Medico
	 * @param especialidadId   Identificador de la instancia de Especialidad
	 * @return Instancia de EspecialidadEntity que fue asociada a Medico
	 */

	@Transactional
	public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un libro al medico con id = {0}", medicoId);
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("Medico no encontrado");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("Especialidd no encontrada");

		especialidadEntity.get().getMedicos().add(medicoEntity.get());
		log.info("Termina proceso de asociarle un libro al medico con id = {0}", medicoId);
		return especialidadEntity.get();
	}

}
