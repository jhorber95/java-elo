/**
 * 
 */
package com.software.estudialo.entities;

import java.util.List;

/**
 * @author LUIS
 *
 */
public class RespuestaGeneral {

	/** The exito. */
	boolean exito;

	int codigo;

	/** The data. */
	List data;

	public RespuestaGeneral() {
		super();
	}

	public RespuestaGeneral(boolean exito, int codigo, List data) {
		super();
		this.exito = exito;
		this.codigo = codigo;
		this.data = data;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	//

}
