package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.services.MedicoService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Medicos
 *
 * @author ISIS2603
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
class MedicoServiceTest {

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<MedicoEntity> medicoList = new ArrayList<>();
	private List<EspecialidadEntity> especialidadList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity");
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidadEntity);
			especialidadList.add(especialidadEntity);
		}

		for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			medicoEntity.setEspecialidad(especialidadList.get(0));
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}

	/**
	 * Prueba para crear un Medico
	 */
	@Test
	void testCreateMedico() throws EntityNotFoundException, IllegalOperationException {
		MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
		newEntity.setEspecialidad(especialidadList.get(0));
		newEntity.setRegistroMedico("RM5673");
		MedicoEntity result = medicoService.createMedico(newEntity);
		assertNotNull(result);
		MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getApellido(), entity.getApellido());
		assertEquals(newEntity.getRegistroMedico(), entity.getRegistroMedico());
	}
	
	/**
	 * Prueba para crear un medico con registro invalido
	 */
	@Test
	void testCreateMedicoConRegistroInvalido() {
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setEspecialidad(especialidadList.get(0));
			newEntity.setRegistroMedico("KN3421");
			medicoService.createMedico(newEntity);
		});
	}
}