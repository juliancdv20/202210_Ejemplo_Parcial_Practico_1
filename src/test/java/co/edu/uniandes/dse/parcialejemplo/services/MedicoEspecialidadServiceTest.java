package co.edu.uniandes.dse.parcialejemplo.service;

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
import co.edu.uniandes.dse.parcialejemplo.entities.EditorialEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.services.MedicoEspecialidadService;
import co.edu.uniandes.dse.parcialejemplo.services.EspecialidadService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Medico - Especialidads
 *
 * @medico ISIS2603
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ MedicoEspecialidadService.class, EspecialidadService.class })
class MedicoEspecialidadServiceTest {

	@Autowired
	private MedicoEspecialidadService medicoEspecialidadService;

	@Autowired
	private EspecialidadService especialidadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private MedicoEntity medico = new MedicoEntity();
	private EspecialidadEntity especialidad = new EspecialidadEntity();
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
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		especialidad = factory.manufacturePojo(EspecialidadEntity.class);
		entityManager.persist(especialidad);

		medico = factory.manufacturePojo(MedicoEntity.class);
		entityManager.persist(medico);

		for (int i = 0; i < 3; i++) {
			EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
			entity.setEditorial(editorial);
			entity.getMedicos().add(medico);
			entityManager.persist(entity);
			especialidadList.add(entity);
			medico.getEspecialidads().add(entity);
		}
	}

	/**
	 * Prueba para asociar un especialidad a un medico.
	 *
	 */
	@Test
	void testAddEspecialidad() throws EntityNotFoundException, IllegalOperationException {
		EspecialidadEntity newEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
		newEspecialidad.setEditorial(editorial);
		especialidadService.createEspecialidad(newEspecialidad);

		EspecialidadEntity especialidadEntity = medicoEspecialidadService.addEspecialidad(medico.getId(), newEspecialidad.getId());
		assertNotNull(especialidadEntity);

		assertEquals(especialidadEntity.getId(), newEspecialidad.getId());
		assertEquals(especialidadEntity.getName(), newEspecialidad.getName());
		assertEquals(especialidadEntity.getDescription(), newEspecialidad.getDescription());
		assertEquals(especialidadEntity.getIsbn(), newEspecialidad.getIsbn());
		assertEquals(especialidadEntity.getImage(), newEspecialidad.getImage());

		EspecialidadEntity lastEspecialidad = medicoEspecialidadService.getEspecialidad(medico.getId(), newEspecialidad.getId());

		assertEquals(lastEspecialidad.getId(), newEspecialidad.getId());
		assertEquals(lastEspecialidad.getName(), newEspecialidad.getName());
		assertEquals(lastEspecialidad.getDescription(), newEspecialidad.getDescription());
		assertEquals(lastEspecialidad.getIsbn(), newEspecialidad.getIsbn());
		assertEquals(lastEspecialidad.getImage(), newEspecialidad.getImage());

	}
	

	/**
	 * Prueba para asociar un especialidad a un medico que no existe.
	 *
	 */

	@Test
	void testAddEspecialidadInvalidMedico() {
		assertThrows(EntityNotFoundException.class, () -> {
			EspecialidadEntity newEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
			newEspecialidad.setEditorial(editorial);
			especialidadService.createEspecialidad(newEspecialidad);
			medicoEspecialidadService.addEspecialidad(0L, newEspecialidad.getId());
		});
	}

	/**
	 * Prueba para asociar un especialidad que no existe a un medico.
	 *
	 */
	@Test
	void testAddInvalidEspecialidad() {
		assertThrows(EntityNotFoundException.class, () -> {
			medicoEspecialidadService.addEspecialidad(medico.getId(), 0L);
		});
	}

}
