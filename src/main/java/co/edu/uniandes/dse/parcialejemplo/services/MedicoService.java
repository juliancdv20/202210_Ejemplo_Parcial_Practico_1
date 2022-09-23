package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Medico.
 */

@Slf4j
@Service
public class MedicoService {

	@Autowired
	MedicoRepository medicoRepository;
	
	/**
	 * Se encarga de crear un Medico en la base de datos.
	 *
	 * @param medico Objeto de MedicoEntity con los datos nuevos
	 * @return Objeto de MedicoEntity con los datos nuevos y su ID.
	 * @throws IllegalOperationException 
	 */
	@Transactional
	public MedicoEntity createMedico(MedicoEntity medico) throws IllegalOperationException {
		log.info("Inicia proceso de creación del medico");
		if(!medico.getRegistroMedico().substring(0,2).matches("RM")) {
			throw new IllegalOperationException("Registro médico no inicia con RM");
	    }
		
		return medicoRepository.save(medico);
	}

}