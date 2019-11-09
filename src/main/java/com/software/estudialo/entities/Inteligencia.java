/**
 * 
 */
package com.software.estudialo.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author LUIS
 *
 */

@Data
@Entity
@Table(name = "inteligencia", schema = "public")
public class Inteligencia {

	@Id
	@Column(name = "int_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@NotNull
	@Column(name = "int_nombre")
	private String nombre;

	@Column(name = "int_descripcion")
	private String descripcion;

}
