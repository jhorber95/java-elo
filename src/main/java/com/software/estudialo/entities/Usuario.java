/**
 * 
 */
package com.software.estudialo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Usuario.
 *
 * @author LUIS
 */
@Entity
@Table(name = "usuario")
public class Usuario extends Persona {

	/** The id. */
	@Id
	private int id;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	/** The estado. */
	private Estado estado;

	/** The roles. */
	private List<Rol> roles;

	private String urlImagen;

	private NivelEducativo nivelEducativo;

	private List<Interes> intereses;

	/**
	 * Instantiates a new usuario.
	 */
	public Usuario() {
		super();
	}

	public Usuario(int id, String email, String password, Estado estado, List<Rol> roles, String urlImagen,
			NivelEducativo nivelEducativo, List<Interes> intereses) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.estado = estado;
		this.roles = roles;
		this.urlImagen = urlImagen;
		this.nivelEducativo = nivelEducativo;
		this.intereses = intereses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public NivelEducativo getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(NivelEducativo nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public List<Interes> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<Interes> intereses) {
		this.intereses = intereses;
	}

}
