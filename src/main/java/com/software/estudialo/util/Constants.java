/**
 * 
 */
package com.software.estudialo.util;

// TODO: Auto-generated Javadoc
/**
 * The Class Constants.
 *
 * @author LUIS
 */
public class Constants {
	
	/*** ------------------------------    DEVELOPMENT      *****/
	/** Ubicacion de las carpetas para las imagenes */
// 	public static final String ROOT_LOCATION = "D://workspace-sts//imagenes-estudialo";
// 	public static final String USUARIO_LOCATION = "D://workspace-sts//imagenes-estudialo//usuario";
// 	public static final String INSTITUCION_LOCATION = "D://workspace-sts//imagenes-estudialo//institucion";
// 	public static final String OFERTA_LOCATION = "D://workspace-sts//imagenes-estudialo//oferta";
// 	public static final String EVENTO_LOCATION = "D://workspace-sts//imagenes-estudialo//evento";
// 	public static final String TEST_LOCATION = "D://workspace-sts//imagenes-estudialo//test";
// 	public static final String PUBLICIDAD_LOCATION = "D://workspace-sts//imagenes-estudialo//publicidad";
// //	
// //	/** RUTA IMAGEN POR DEFECTO DESARROLLO -----*/
// 	public static final String DEFECTO_IMAGE = "default.png";
// 	public static final String DEFECTO_USUARIO_IMAGE = "D://workspace-sts//imagenes-estudialo//usuario//default.png";
// 	public static final String DEFECTO_INSTITUCION_IMAGE = "D://workspace-sts//imagenes-estudialo//institucion//default.png";
// 	public static final String DEFECTO_OFERTA_IMAGE = "D://workspace-sts//imagenes-estudialo//oferta//default.png";
// 	public static final String DEFECTO_EVENTO_IMAGE = "D://workspace-sts//imagenes-estudialo//evento//default.png";
	
	
	/*** ------------------------------    PRODUCTION      *****/
	// Las rutas en linux se pueden asi:
	// /root/estudialo/imagenes-estudialo/usuario
	// ./imagenes-estudialo/institucion
	
	/** Ubicacion de las carpetas para las imagenes */
	public static final String ROOT_LOCATION = "./imagenes-estudialo";
	public static final String USUARIO_LOCATION = "./imagenes-estudialo/usuario";
	public static final String INSTITUCION_LOCATION = "./imagenes-estudialo/institucion";
	public static final String OFERTA_LOCATION = "./imagenes-estudialo/oferta";
	public static final String EVENTO_LOCATION = "./imagenes-estudialo/evento";
	public static final String TEST_LOCATION = "./imagenes-estudialo/test";
	 public static final String PUBLICIDAD_LOCATION = "./imagenes-estudialo/publicidad";

	/** RUTA IMAGEN POR DEFECTO DESARROLLO -----*/
	public static final String DEFECTO_IMAGE = "default.png";
	public static final String DEFECTO_USUARIO_IMAGE = "./imagenes-estudialo/usuario/default.png";
	public static final String DEFECTO_INSTITUCION_IMAGE = "./imagenes-estudialo/institucion/default.png";
	public static final String DEFECTO_OFERTA_IMAGE = "./imagenes-estudialo/oferta/default.png";
	public static final String DEFECTO_EVENTO_IMAGE = "./imagenes-estudialo/evento/default.png";
	
	
	/** Respuestas para imagenes **/
	public static final String IMAGEN_SUBIDA_CORRECTAMENTE = "Imagen Subida Correctamente";
	
	
	/** The Constant ID_ROL_ESTUDIANTE. */
	public static final int ID_ROL_ESTUDIANTE = 2;
	
	/** The Constant ID_ROL_ADMINISTRADOR. */
	public static final int ID_ROL_ADMINISTRADOR = 1;
	
	/** The Constant ID_ROL_FREELANCER. */
	public static final int ID_ROL_FREELANCER = 3;
	
	/** The Constant ID_ROL_PUBLICADOR. */
	public static final int ID_ROL_PUBLICADOR = 4;
	
	/** The Constant ID_ROL_INSTITUCION. */
	public static final int ID_ROL_INSTITUCION = 5;
	
	/** The Constant ID_TIPO_OFRECE_INSTITUCION. */
	public static final int ID_TIPO_OFRECE_INSTITUCION = 1;
	
	/** The Constant ID_TIPO_OFRECE_FREELANCER. */
	public static final int ID_TIPO_OFRECE_FREELANCER = 2;
	
	public static final int ID_ESTADO_NO_REVISADO_DENUNCIA = 11;
	
	public static final int ID_ESTADO_REVISADO_DENUNCIA = 10;
	
	/** The Constant ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS. */
	public static final int ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS = 1;
	
	/** The Constant ID_ESTADO_ACTIVO_ENTIDADES_SECUNDARIAS. */
	public static final int ID_ESTADO_ACTIVO_ENTIDADES_SECUNDARIAS = 8;
	
	public static final int ID_ESTADO_INACTIVO_ENTIDADES_SECUNDARIAS = 9;
	
	/** The Constant ID_ESTADO_PREINSCRITO. */
	public static final int ID_ESTADO_PREINSCRITO = 5;
	
	/** The Constant ID_ESTADO_INSCRITO. */
	public static final int ID_ESTADO_INSCRITO = 6;
	
	/** The Constant ID_ESTADO_INSCRITO. */
	public static final int ID_ESTADO_NO_INSCRITO = 13;  // (RECHAZADO)
	
	/** The Constant ID_CONFIRMADO. */
	public static final Boolean ID_CONFIRMADO = true;
	
	/** The Constant ID_NO_CONFIRMADO. */
	public static final Boolean ID_NO_CONFIRMADO = false;
	
	/** The Constant USUARIO_NO_ENCONTRADO. */
	public static final String USUARIO_NO_ENCONTRADO = "Usuario no Encontrado";
	
	public static final int CALIFICACION_REALIZADA = 1;
	
	public static final int CALIFICACION_NO_REALIZADA = 0;
	
	public static final String CALIFICACION_EXISTENTE = "Ya califico la Oferta";
	
	public static final String INCRIPCION_NO_ENCONTRADA = "No se encontro la inscripcion";
	
	public static final String INCRIPCION_NO_CONFIRMADA_POR_DOCENTE = "El freelancer no ha confirmado la inscripcion, no puede calificar";
	
	public static final String INCRIPCION_YA_CONFIRMADA_POR_DOCENTE = "El freelancer ya confirmo la inscripcion ya puedes calificar";
	
}