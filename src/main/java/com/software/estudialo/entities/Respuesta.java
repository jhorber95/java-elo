package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Respuesta.
 */
public class Respuesta {
	
	/** The Constant OPERACION_EJECUTADA_EXITOSAMENTE. */
	public static final String OPERACION_EJECUTADA_EXITOSAMENTE = "La operacion fue realizada exitosamente.";
	
	public static final String CLAVE_CAMBIADA_EXITOSAMENTE = "Cambio de credenciales realizada exitosamente.";
	
	/** The Constant ERROR_EJECUTAR_OPERACION. */
	public static final String ERROR_EJECUTAR_OPERACION = "Ocurrio un error en la ejecucion de la operacion, comuniquese con mantenimiento.";	
	
	/** The Constant INSCRIPCION_USUARIO_EXITOSA. */
	public static final String INSCRIPCION_USUARIO_EXITOSA = "La inscripcion se realizo correctamente.";
	
	/** The Constant USUARIO_EXISTE_ERROR. */
	public static final String USUARIO_EXISTE_ERROR = "El usuario ya existe.";
	
	/** The Constant INSCRIPCION_USUARIO_ERROR. */
	public static final String INSCRIPCION_USUARIO_ERROR = "No se pudo inscribir el usuario.";	
	
	/** The Constant MODIFICACION_USUARIO_EXITOSA. */
	public static final String MODIFICACION_USUARIO_EXITOSA = "La modificacion se realizo correctamente.";
	
	/** The Constant USUARIO_NO_EXISTE_ERROR. */
	public static final String USUARIO_NO_EXISTE_ERROR = "El usuario no existe.";
	
	/** The Constant MODIFICACION_USUARIO_ERROR. */
	public static final String MODIFICACION_USUARIO_ERROR = "No se pudo modificar el usuario.";	
	
	/** The Constant ELIMINACION_USUARIO_EXITOSA. */
	public static final String ELIMINACION_USUARIO_EXITOSA = "La eliminacion se realizo correctamente.";
	
	/** The Constant ELIMINACION_USUARIO_ERROR. */
	public static final String ELIMINACION_USUARIO_ERROR = "No se pudo eliminar el usuario.";
	
	
	/** The Constant VALOR_NULL_ERROR. */
	public static final String VALOR_NULL_ERROR = "No se permiten valores nulos.";	
	
	/** The Constant VALOR_ID_NULL_ERROR. */
	public static final String VALOR_ID_NULL_ERROR = "El id Nulo no esta permitido.";
	
	
	/** The Constant TIPO_RESPUESTA. */
	public static final String TIPO_RESPUESTA = "Respuesta.";
	
	/** The Constant TIPO_DATOS. */
	public static final String TIPO_DATOS = "Datos.";
	
	public static final String TOKEN_RESET_PASSWORD = "Token generado exitosamente";
	

	/** The exito. */
	boolean exito;
	
	/** The tipo. */
	private String tipo;
	
	/** The mensaje. */
	private String mensaje;
	
	/** The body. */
	private Object body;

	/**
	 * Checks if is exito.
	 *
	 * @return true, if is exito
	 */
	public boolean isExito() {
		return exito;
	}

	/**
	 * Sets the exito.
	 *
	 * @param exito the new exito
	 */
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	
	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Gets the mensaje.
	 *
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Sets the mensaje.
	 *
	 * @param mensaje            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body            the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}

	

}