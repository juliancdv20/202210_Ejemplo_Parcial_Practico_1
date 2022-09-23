package co.edu.uniandes.dse.parcialejemplo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class EspecialidadEntity extends BaseEntity{
	private String nombre;
	private String descripcion;
	
	@PodamExclude
	@OneToMany(mappedBy = "especialidad")
	private List<MedicoEntity> medicos = new ArrayList<>();
}