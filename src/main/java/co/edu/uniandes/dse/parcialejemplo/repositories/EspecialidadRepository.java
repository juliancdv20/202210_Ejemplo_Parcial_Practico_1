package co.edu.uniandes.dse.parcialejemplo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {
	List<EspecialidadEntity> findByNombre(String nombre);
}