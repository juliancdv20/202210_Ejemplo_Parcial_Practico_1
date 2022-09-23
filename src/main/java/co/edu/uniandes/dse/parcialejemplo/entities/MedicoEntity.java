package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class MedicoEntity extends BaseEntity{
	private String nombre;
	private String apellido;
	private String registroMedico;
	
	@PodamExclude
	@ManyToOne
	private EspecialidadEntity especialidad;
}
