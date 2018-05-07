/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.EstudianteDao;
import com.software.estudialo.dao.InscripcionOfertaInstitucionDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.impl.InscripcionOfertaFreelancerDaoImpl.InscripcionOfertaFreelancerRowMapper;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Estudiante;
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.InscripcionOfertaInstitucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaInstitucionDaoImpl.
 *
 * @author LUIS
 */
@Repository("inscripcionOfertaInstitucionDao")
public class InscripcionOfertaInstitucionDaoImpl implements InscripcionOfertaInstitucionDao{	
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InscripcionOfertaInstitucionDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;
	
	/** The estudiante dao. */
	@Autowired
	EstudianteDao estudianteDao;
	
	/** The oferta dao. */
	@Autowired
	OfertaDao ofertaDao;

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaInstitucionDao#obtenerinscripcionOfertaInstitucion(int)
	 */
	@Override
	public InscripcionOfertaInstitucion obtenerinscripcionOfertaInstitucion(int id) {
		logger.debug("obtenerinscripcionOfertaInstitucion -- Buscando inscripcion oferta institucion por Id ");

		String SQL = "SELECT ioi.ioi_id, ioi.ioi_oferta_institucion, ioi.ioi_estudiante, ioi.ioi_confirmacion, ioi.ioi_fecha_hora, ioi.ioi_estado "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "WHERE ioi.ioi_id = ?;";

		InscripcionOfertaInstitucion inscripcionOfertaInstitucionResponse = jdbcTemplate.query(SQL, new Object[] { id }, new InscripcionOfertaInstitucionResultSetExtractor());
		
		logger.debug("obtenerinscripcionOfertaInstitucion -- Saliendo inscripcion oferta institucion por Id ");
		return inscripcionOfertaInstitucionResponse;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaInstitucionDao#inscribirEstudiante(int, int)
	 */
	@Override
	public Boolean inscribirEstudiante(int idOfertaInstitucion, int idEstudiante) {
		logger.debug("--- INSCRIBIENDO ESTUDIANTE A OFERTA ------");

		try {

			String SQL1 = "INSERT INTO inscripcion_oferta_institucion (ioi_oferta_institucion, ioi_estudiante, ioi_confirmacion, ioi_fecha_hora, ioi_estado) VALUES (?, ?, ?, ?, ?);";
			
			java.util.Date date = new java.util.Date(System.currentTimeMillis());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());		
			
			int resultado1 = jdbcTemplate.update(SQL1, idOfertaInstitucion, idEstudiante, Constants.ID_NO_CONFIRMADO, timestamp, Constants.ID_ESTADO_PREINSCRITO);

			if (resultado1 > 0) {
				logger.debug("Se inscribio el estudiante correctamente");
				return true;								
				
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaInstitucionDao#obtenerIdOfertaInstitucion(int, int)
	 */
	@Override
	public int obtenerIdOfertaInstitucion(int idOferta, int idInstitucion) {
		logger.debug("------- Buscando al id oferta institucion por el id idOferta y el id institucion");
		
		String sql = "SELECT oi.oins_id "
				+ "FROM oferta_institucion oi "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN institucion i ON i.inst_id = oi.oins_institucion "
				+ "WHERE oi.oins_oferta = ? AND oi.oins_institucion = ?; ";

		int idOfertaInstitucion = jdbcTemplate.queryForObject(sql, new Object[]{idOferta, idInstitucion}, Integer.class);
						
		return idOfertaInstitucion;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaInstitucionDao#buscarInscripcionOfertaInstitucion(int, int)
	 */
	@Override
	public Boolean buscarInscripcionOfertaInstitucion(int idEstudiante, int idOfertaInstitucion) {
		logger.debug("------- Buscando si existe alguna inscripcion de un estudiante a un oferta ofrecida por un institucion");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "WHERE ioi.ioi_estudiante = ? AND ioi.ioi_oferta_institucion = ?; ";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idEstudiante, idOfertaInstitucion }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaInstitucionDao#listarInscripcionOfertaInstituciones(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaInstituciones(String search, int start, int length, int draw,
			int posicion, String direccion) {
		
JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ioi.ioi_id", "o.ofe_titulo", "u.usu_email", "e.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN estado e ON e.est_id = ioi.ioi_estado "
				+ "WHERE ioi.ioi_estado IN (5, 6) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de Inscripcion Oferta Institucions filtrados: " + sql);
		
			
		sql = "SELECT ioi_id, ofe_titulo, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ioi.ioi_id, o.ofe_titulo, u.usu_email, e.est_nombre "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN estado e ON e.est_id = ioi.ioi_estado "
				+ "WHERE ioi.ioi_estado IN (5, 6) "
				+ "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de Inscripcion Oferta Institucions filtrados:: " + sql);

		List<InscripcionOfertaInstitucion> listaInscripcionOfertaInstitucion = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<InscripcionOfertaInstitucion>() {

					public InscripcionOfertaInstitucion mapRow(ResultSet rs, int rowNum) throws SQLException {
																	
						InscripcionOfertaInstitucion inscripcionOfertaInstitucion = obtenerinscripcionOfertaInstitucion(rs.getInt("ioi_id"));
						return inscripcionOfertaInstitucion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaInscripcionOfertaInstitucion);

		return respuesta;
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaInstitucionDao#listarInscripcionOfertaInstitucion(int, java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaInstitucion(int idInstitucion, String search, int start, int length,
			int draw, int posicion, String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ioi.ioi_id", "o.ofe_titulo", "u.usu_email", "e.est_nombre" };
		
		int fin = start + length - 1;
		
		
		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN estado e ON e.est_id = ioi.ioi_estado "
				+ "INNER JOIN institucion i ON i.inst_id = oi.oins_institucion "
				+ "WHERE ioi.ioi_estado IN (5, 6) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND i.inst_id = ? AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { idInstitucion ,"&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de Inscripcion Oferta Institucions filtrados: " + sql);
		
			
		sql = "SELECT ioi_id, ofe_titulo, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ioi.ioi_id, o.ofe_titulo, u.usu_email, e.est_nombre "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN estado e ON e.est_id = ioi.ioi_estado "
				+ "INNER JOIN institucion i ON i.inst_id = oi.oins_institucion "
				+ "WHERE ioi.ioi_estado IN (5, 6) AND i.inst_id = ? "
				+ "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de Inscripcion Oferta Institucions filtrados:: " + sql);

		List<InscripcionOfertaInstitucion> listaInscripcionOfertaInstitucion = jdbcTemplate.query(sql, 
				new Object[] { idInstitucion ,"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<InscripcionOfertaInstitucion>() {

					public InscripcionOfertaInstitucion mapRow(ResultSet rs, int rowNum) throws SQLException {
																	
						InscripcionOfertaInstitucion inscripcionOfertaInstitucion = obtenerinscripcionOfertaInstitucion(rs.getInt("ioi_id"));
						return inscripcionOfertaInstitucion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);	
		respuesta.setData(listaInscripcionOfertaInstitucion);

		return respuesta;
	}
	
	
	/**
	 * The Class InscripcionOfertaInstitucionResultSetExtractor.
	 */
	class InscripcionOfertaInstitucionResultSetExtractor implements ResultSetExtractor<InscripcionOfertaInstitucion>{

				
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public InscripcionOfertaInstitucion extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("InscripcionOfertaInstitucion -- Obteniendo Inscripcion Oferta Institucion");
				InscripcionOfertaInstitucion inscripcionOfertaInstitucionRow = new InscripcionOfertaInstitucion();
				// El id es el del usuario			
				inscripcionOfertaInstitucionRow.setId(rs.getInt("ioi_id"));
				inscripcionOfertaInstitucionRow.setConfirmacion(rs.getBoolean("ioi_confirmacion"));
				inscripcionOfertaInstitucionRow.setFechaHora(rs.getTimestamp("ioi_fecha_hora"));
				
				
				Estado estado = estadoDao.obtenerEstado(rs.getInt("ioi_estado"));
				Estudiante estudiante = estudianteDao.obtenerEstudiante(rs.getInt("ioi_estudiante"));
				
				logger.debug("------------ El id de la oferta institucion es : " + rs.getInt("ioi_oferta_institucion"));
				int idOferta = ofertaDao.obtenerIdOfertaPorIdOfertaInstitucion(rs.getInt("ioi_oferta_institucion"));
				
				logger.debug("------------ El id de la oferta es : " + idOferta);
				Oferta oferta = ofertaDao.obtenerOferta(idOferta);								
								
				inscripcionOfertaInstitucionRow.setEstado(estado);
				inscripcionOfertaInstitucionRow.setEstudiante(estudiante);
				inscripcionOfertaInstitucionRow.setOferta(oferta);
				return inscripcionOfertaInstitucionRow;
			}
			return null;
		}
		
	}
	
	
	class InscripcionOfertaInstitucionRowMapper implements RowMapper<InscripcionOfertaInstitucion>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public InscripcionOfertaInstitucion mapRow(ResultSet rs, int rowNum) throws SQLException {
    		InscripcionOfertaInstitucion inscripcionOfertaInstitucionRow = new InscripcionOfertaInstitucion();
			// El id es el del usuario			
			inscripcionOfertaInstitucionRow.setId(rs.getInt("ioi_id"));
			inscripcionOfertaInstitucionRow.setConfirmacion(rs.getBoolean("ioi_confirmacion"));
			inscripcionOfertaInstitucionRow.setFechaHora(rs.getTimestamp("ioi_fecha_hora"));
			
			
			Estado estado = estadoDao.obtenerEstado(rs.getInt("ioi_estado"));
			Estudiante estudiante = estudianteDao.obtenerEstudiante(rs.getInt("ioi_estudiante"));
			
			logger.debug("------------ El id de la oferta institucion es : " + rs.getInt("ioi_oferta_institucion"));
			int idOferta = ofertaDao.obtenerIdOfertaPorIdOfertaInstitucion(rs.getInt("ioi_oferta_institucion"));
			
			logger.debug("------------ El id de la oferta es : " + idOferta);
			Oferta oferta = ofertaDao.obtenerOferta(idOferta);								
							
			inscripcionOfertaInstitucionRow.setEstado(estado);
			inscripcionOfertaInstitucionRow.setEstudiante(estudiante);
			inscripcionOfertaInstitucionRow.setOferta(oferta);
			return inscripcionOfertaInstitucionRow;
	    }
	}
	
	
	


	@Override
	public Boolean buscarInscripcionOfertaInstitucionConEstudiante(int idOferta, int idEstudiante) {

		logger.debug("------- Buscando si existe alguna inscripcion de un estudiante a un oferta ofrecida por una institucion");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante e ON e.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "AND o.ofe_id = ? AND e.estu_id = ?; 	";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta, idEstudiante }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public int obtenerIdInscripcionOfertaInstitucion(int idOferta, int idEstudiante) {
		
		logger.debug("------- Buscando al id inscripcion oferta institucion por el id idOferta y el id int idOferta, int idEstudiante");
		
		String sql = "SELECT ioi.ioi_id "
				+ "FROM inscripcion_oferta_institucion ioi, estudiante e, oferta_institucion oi, oferta o "
				+ "WHERE oi.oins_id = ioi.ioi_oferta_institucion AND e.estu_id = ioi.ioi_estudiante AND o.ofe_id = oi.oins_oferta "
				+ "AND o.ofe_id = ? AND e.estu_id = ?; ";

		int idInscripcionOfertaFreelancer = jdbcTemplate.queryForObject(sql, new Object[]{idOferta, idEstudiante}, Integer.class);
						
		return idInscripcionOfertaFreelancer;
		
	}

	@Override
	public Boolean confirmarInscripcionEstudiante(int idInscripcionOfertaInstitucion) {
	
		logger.debug("--- DENTRO DE confirmar la inscripcion del estudiante en la oferta institucion--------");

		String SQL1 = "UPDATE inscripcion_oferta_institucion SET ioi_estado = ?, ioi_confirmacion = ? "
				+ "WHERE ioi_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, Constants.ID_ESTADO_INSCRITO, true,  idInscripcionOfertaInstitucion);

		if (resultado1 > 0) {
			logger.debug("--- realizado -----");
			return true;
		} else {
			logger.debug("No se pudo");
			return false;
		}
		
	}
	
	
	@Override
	public List<InscripcionOfertaInstitucion> obtenerInscripcionOfertaInstitucionDeEstudiante(int idEstudiante) {
		
		logger.debug("obtenerInscripcionOfertaInstitucionDeEstudiante -- Buscando las inscripciones ");
		
		String sql = "SELECT ioi_id, ioi_oferta_institucion, ioi_estudiante, ioi_confirmacion, ioi_fecha_hora, ioi_estado "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN oferta_institucion oi ON ioi.ioi_oferta_institucion = oi.oins_id "
				+ "WHERE ioi.ioi_estado IN (6, 5) AND ioi.ioi_estudiante = ? ";
		
		
		List<InscripcionOfertaInstitucion> listaInscripcion = jdbcTemplate.query(sql, new Object[]{idEstudiante}, new InscripcionOfertaInstitucionRowMapper());
		logger.debug("obtenerInscripcionOfertaInstitucionDeEstudiante -- Saliendo de busqueda de las categorias ");
		
		return listaInscripcion;		
	}

	@Override
	public JSONRespuesta listarPreInscripcionesOfertaInstitucion(int idInstitucion, String search, int start,
			int length, int draw, int posicion, String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ioi.ioi_id", "o.ofe_titulo", "u.usu_email", "e.est_nombre" };
		
		int fin = start + length - 1;
		
		
		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN estado e ON e.est_id = ioi.ioi_estado "
				+ "INNER JOIN institucion i ON i.inst_id = oi.oins_institucion "
				+ "WHERE ioi.ioi_estado IN (5, 6, 13) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND i.inst_id = ? AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { idInstitucion ,"&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de Inscripcion Oferta Institucions filtrados: " + sql);
		
			
		sql = "SELECT ioi_id, ofe_titulo, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ioi.ioi_id, o.ofe_titulo, u.usu_email, e.est_nombre "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "INNER JOIN estudiante est ON est.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "INNER JOIN estado e ON e.est_id = ioi.ioi_estado "
				+ "INNER JOIN institucion i ON i.inst_id = oi.oins_institucion "
				+ "WHERE ioi.ioi_estado IN (5, 6, 13) AND i.inst_id = ? "
				+ "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de Inscripcion Oferta Institucions filtrados:: " + sql);

		List<InscripcionOfertaInstitucion> listaInscripcionOfertaInstitucion = jdbcTemplate.query(sql, 
				new Object[] { idInstitucion ,"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<InscripcionOfertaInstitucion>() {

					public InscripcionOfertaInstitucion mapRow(ResultSet rs, int rowNum) throws SQLException {
																	
						InscripcionOfertaInstitucion inscripcionOfertaInstitucion = obtenerinscripcionOfertaInstitucion(rs.getInt("ioi_id"));
						return inscripcionOfertaInstitucion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);	
		respuesta.setData(listaInscripcionOfertaInstitucion);

		return respuesta;
		
	}

	@Override
	public Boolean rechazarInscripcionEstudiante(int idInscripcionOfertaInstitucion) {
		
		logger.debug("--- DENTRO DE rechazar la inscripcion del estudiante en la oferta institucion--------");

		String SQL1 = "UPDATE inscripcion_oferta_institucion SET ioi_estado = ?, ioi_confirmacion = ?  "
				+ "WHERE ioi_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, Constants.ID_ESTADO_NO_INSCRITO, false,  idInscripcionOfertaInstitucion);

		if (resultado1 > 0) {
			logger.debug("--- realizado -----");
			return true;
		} else {
			logger.debug("No se pudo");
			return false;
		}
		
	}

	@Override
	public boolean eliminarInscripcionInstitucion(int idInscripcion) {
		logger.debug("---JB Dentro de eliminarInscripcionInstitucion");
		
		String sql = "UPDATE inscripcion_oferta_institucion SET ioi_estado=7" + 
					 " WHERE ioi_id=?";
		
		int resultado1 = jdbcTemplate.update(sql, idInscripcion);

		if (resultado1 > 0) {
			logger.debug("--- INSCRIPCION ELIMINADA -----");
			return true;
		} else {
			logger.debug("No se pudo ELIMINAR LA INSCRIPCION DE INSTITUCION");
			return false;
		}
	}


	
	
	
	

}
