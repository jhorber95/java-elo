/**
 * 
 */
package com.software.estudialo.entities;

import java.sql.Date;

/**
 * @author LUIS
 *
 */
public class Denuncia {

	private int id;
	private String mensaje;
	private Usuario usuario;
	private Date fecha;
	private Estado estado;

	public Denuncia() {
		super();
	}

	public Denuncia(int id, String mensaje, Usuario usuario, Date fecha, Estado estado) {
		super();
		this.id = id;
		this.mensaje = mensaje;
		this.usuario = usuario;
		this.fecha = fecha;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
