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
import com.software.estudialo.dao.InscripcionOfertaFreelancerDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Estudiante;
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class InscripcionOfertaFreelancerDaoImpl.
 *
 * @author LUIS
 */
@Repository("inscripcionOfertaFreelancerDao")
public class InscripcionOfertaFreelancerDaoImpl implements InscripcionOfertaFreelancerDao {  
	
	/** The logger. */
	private Logger logger = Logger.getLogger(InscripcionOfertaFreelancerDaoImpl.class);

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
	 * @see com.software.estudialo.dao.InscripcionOfertaFreelancerDao#obtenerinscripcionOfertaFreelancer(int)
	 */
	@Override
	public InscripcionOfertaFreelancer obtenerinscripcionOfertaFreelancer(int id) {
		
		logger.debug("obtenerinscripcionOfertaFreelancer -- Buscando inscripcion oferta freelancer por Id ");

		String SQL = "SELECT iof.iof_id, iof.iof_oferta_freelancer, iof.iof_estudiante, iof.iof_confirmacion, iof.iof_fecha_hora, iof.iof_estado "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "WHERE iof.iof_id = ?;";

		InscripcionOfertaFreelancer inscripcionOfertaFreelancerResponse = jdbcTemplate.query(SQL, new Object[] { id }, new InscripcionOfertaFreelancerResultSetExtractor());
		
		logger.debug("obtenerinscripcionOfertaFreelancer -- Saliendo inscripcion oferta freelancer por Id ");
		return inscripcionOfertaFreelancerResponse;		
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaFreelancerDao#listarInscripcionOfertaFreelancers(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaFreelancers(String search, int start, int length, int draw,
			int posicion, String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "iof.iof_id", "o.ofe_titulo", "u.usu_email", "e.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN estado e ON e.est_id = iof.iof_estado "
				+ "WHERE iof.iof_estado IN (5, 6) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de Inscripcion Oferta Freelancers filtrados: " + sql);
		
			
		sql = "SELECT iof_id, ofe_titulo, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "iof.iof_id, o.ofe_titulo, u.usu_email, e.est_nombre "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN estado e ON e.est_id = iof.iof_estado "
				+ "WHERE iof.iof_estado IN (5, 6) "
				+ "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de Inscripcion Oferta Freelancers filtrados:: " + sql);

		List<InscripcionOfertaFreelancer> listaInscripcionOfertaFreelancer = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<InscripcionOfertaFreelancer>() {

					public InscripcionOfertaFreelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
																	
						InscripcionOfertaFreelancer inscripcionOfertaFreelancer = obtenerinscripcionOfertaFreelancer(rs.getInt("iof_id"));
						return inscripcionOfertaFreelancer;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaInscripcionOfertaFreelancer);

		return respuesta;
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaFreelancerDao#listarInscripcionOfertaFreelancer(int, java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInscripcionOfertaFreelancer(int idFreelancer, String search, int start, int length,
			int draw, int posicion, String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "iof.iof_id", "o.ofe_titulo", "u.usu_email", "e.est_nombre" };
		
		int fin = start + length - 1;
		
		
		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN estado e ON e.est_id = iof.iof_estado "
				+ "INNER JOIN freelancer f ON f.free_id = of.ofr_freelancer "
				+ "WHERE iof.iof_estado IN (6) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND f.free_id = ? AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { idFreelancer ,"&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de Inscripcion Oferta Freelancers filtrados: " + sql);
		
			
		sql = "SELECT iof_id, ofe_titulo, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "iof.iof_id, o.ofe_titulo, u.usu_email, e.est_nombre "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN estado e ON e.est_id = iof.iof_estado "
				+ "INNER JOIN freelancer f ON f.free_id = of.ofr_freelancer "
				+ "WHERE iof.iof_estado IN (6) AND f.free_id = ? "
				+ "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de Inscripcion Oferta Freelancers filtrados:: " + sql);

		List<InscripcionOfertaFreelancer> listaInscripcionOfertaFreelancer = jdbcTemplate.query(sql, 
				new Object[] { idFreelancer ,"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<InscripcionOfertaFreelancer>() {

					public InscripcionOfertaFreelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
																	
						InscripcionOfertaFreelancer inscripcionOfertaFreelancer = obtenerinscripcionOfertaFreelancer(rs.getInt("iof_id"));
						return inscripcionOfertaFreelancer;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);	
		respuesta.setData(listaInscripcionOfertaFreelancer);

		return respuesta;
	}
	
	
	
	
	
	/**
	 * The Class InscripcionOfertaFreelancerResultSetExtractor.
	 */
	class InscripcionOfertaFreelancerResultSetExtractor implements ResultSetExtractor<InscripcionOfertaFreelancer>{

		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public InscripcionOfertaFreelancer extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("InscripcionOfertaFreelancer -- Obteniendo Inscripcion Oferta Freelancer");
				InscripcionOfertaFreelancer inscripcionOfertaFreelancerRow = new InscripcionOfertaFreelancer();
				// El id es el del usuario			
				inscripcionOfertaFreelancerRow.setId(rs.getInt("iof_id"));
				inscripcionOfertaFreelancerRow.setConfirmacion(rs.getBoolean("iof_confirmacion"));
				inscripcionOfertaFreelancerRow.setFechaHora(rs.getTimestamp("iof_fecha_hora"));
				
				
				Estado estado = estadoDao.obtenerEstado(rs.getInt("iof_estado"));
				Estudiante estudiante = estudianteDao.obtenerEstudiante(rs.getInt("iof_estudiante"));
				
				logger.debug("------------ El id de la oferta freelancer es : " + rs.getInt("iof_oferta_freelancer"));
				int idOferta = ofertaDao.obtenerIdOfertaPorIdOfertaFreelancer(rs.getInt("iof_oferta_freelancer"));
				
				logger.debug("------------ El id de la oferta es : " + idOferta);
				Oferta oferta = ofertaDao.obtenerOferta(idOferta);								
								
				inscripcionOfertaFreelancerRow.setEstado(estado);
				inscripcionOfertaFreelancerRow.setEstudiante(estudiante);
				inscripcionOfertaFreelancerRow.setOferta(oferta);
				return inscripcionOfertaFreelancerRow;
			}
			return null;
		}
		
	}
	
	
	class InscripcionOfertaFreelancerRowMapper implements RowMapper<InscripcionOfertaFreelancer>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public InscripcionOfertaFreelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
    		InscripcionOfertaFreelancer inscripcionOfertaFreelancerRow = new InscripcionOfertaFreelancer();
			// El id es el del usuario			
			inscripcionOfertaFreelancerRow.setId(rs.getInt("iof_id"));
			inscripcionOfertaFreelancerRow.setConfirmacion(rs.getBoolean("iof_confirmacion"));
			inscripcionOfertaFreelancerRow.setFechaHora(rs.getTimestamp("iof_fecha_hora"));
			
			
			Estado estado = estadoDao.obtenerEstado(rs.getInt("iof_estado"));
			Estudiante estudiante = estudianteDao.obtenerEstudiante(rs.getInt("iof_estudiante"));
			
			logger.debug("------------ El id de la oferta freelancer es : " + rs.getInt("iof_oferta_freelancer"));
			int idOferta = ofertaDao.obtenerIdOfertaPorIdOfertaFreelancer(rs.getInt("iof_oferta_freelancer"));
			
			logger.debug("------------ El id de la oferta es : " + idOferta);
			Oferta oferta = ofertaDao.obtenerOferta(idOferta);								
							
			inscripcionOfertaFreelancerRow.setEstado(estado);
			inscripcionOfertaFreelancerRow.setEstudiante(estudiante);
			inscripcionOfertaFreelancerRow.setOferta(oferta);
			return inscripcionOfertaFreelancerRow;	        
	    }
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaFreelancerDao#inscribirEstudiante(int, int)
	 */
	@Override
	public Boolean inscribirEstudiante(int idOfertaFreelancer, int idEstudiante) {
		
		logger.debug("--- INSCRIBIENDO ESTUDIANTE A OFERTA ------");

		try {

			String SQL1 = "INSERT INTO inscripcion_oferta_freelancer (iof_oferta_freelancer, iof_estudiante, iof_confirmacion, iof_fecha_hora, iof_estado) VALUES (?, ?, ?, ?, ?);";
			
			java.util.Date date = new java.util.Date(System.currentTimeMillis());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());		
			
			int resultado1 = jdbcTemplate.update(SQL1, idOfertaFreelancer, idEstudiante, Constants.ID_NO_CONFIRMADO, timestamp, Constants.ID_ESTADO_PREINSCRITO);

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
	 * @see com.software.estudialo.dao.InscripcionOfertaFreelancerDao#obtenerIdOfertaFreelancer(int, int)
	 */
	@Override
	public int obtenerIdOfertaFreelancer(int idOferta, int idFreelancer) {
		
		logger.debug("------- Buscando al id oferta freelancer por el id idOferta y el id freelancer");
		
		String sql = "SELECT of.ofr_id "
				+ "FROM oferta_freelancer of "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN freelancer f ON f.free_id = of.ofr_freelancer "
				+ "WHERE of.ofr_oferta = ? AND of.ofr_freelancer = ?; ";

		int idOfertaFreelancer = jdbcTemplate.queryForObject(sql, new Object[]{idOferta, idFreelancer}, Integer.class);
						
		return idOfertaFreelancer;
		
	}

	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InscripcionOfertaFreelancerDao#buscarInscripcionOfertaFreelancer(int, int)
	 */
	@Override
	public Boolean buscarInscripcionOfertaFreelancer(int idEstudiante, int idOfertaFreelancer) {
		
		logger.debug("------- Buscando si existe alguna inscripcion de un estudiante a un oferta ofrecida por un freelancer");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "WHERE iof.iof_estudiante = ? AND iof.iof_oferta_freelancer = ?; ";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idEstudiante, idOfertaFreelancer }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}


	@Override
	public int obtenerIdInscripcionOfertaFreelancer(int idOferta, int idEstudiante) {
		
		logger.debug("------- Buscando al id inscripcion oferta freelancer por el id idOferta y el id int idOferta, int idEstudiante");
		
		String sql = "SELECT iof.iof_id "
				+ "FROM inscripcion_oferta_freelancer iof, estudiante e, oferta_freelancer ofr, oferta o "
				+ "WHERE ofr.ofr_id = iof.iof_oferta_freelancer AND e.estu_id = iof.iof_estudiante AND o.ofe_id = ofr.ofr_oferta "
				+ "AND o.ofe_id = ? AND e.estu_id = ?;  ";

		int idInscripcionOfertaFreelancer = jdbcTemplate.queryForObject(sql, new Object[]{idOferta, idEstudiante}, Integer.class);
						
		return idInscripcionOfertaFreelancer;
		
	}


	@Override
	public Boolean buscarInscripcionOfertaFreelancerConEstudiante(int idOferta, int idEstudiante) {
		
		logger.debug("------- Buscando si existe alguna inscripcion de un estudiante a un oferta ofrecida por un freelancer");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante e ON e.estu_id = iof.iof_estudiante "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "AND o.ofe_id = ? AND e.estu_id = ?; 	";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta, idEstudiante }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}


	@Override
	public Boolean confirmarInscripcionEstudiante(int idInscripcionOfertaFreelancer) {
		logger.debug("--- DENTRO DE confirmar la inscripcion del estudiante en la oferta freelancer--------");

		String SQL1 = "UPDATE inscripcion_oferta_freelancer SET iof_estado = ?, ioF_confirmacion = ?  "
				+ "WHERE iof_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, Constants.ID_ESTADO_INSCRITO, true,  idInscripcionOfertaFreelancer);

		if (resultado1 > 0) {
			logger.debug("--- realizado -----");
			return true;
		} else {
			logger.debug("No se pudo");
			return false;
		}
	}


	@Override
	public List<InscripcionOfertaFreelancer> obtenerInscripcionesOfertasFreelancerDeEstudiante(int idEstudiante) {
		
		logger.debug("obtenerOfertasFreelancerDeEstudiante -- Buscando las inscripciones ");
		
		String sql = "SELECT iof_id, iof_oferta_freelancer, iof_estudiante, iof_confirmacion, iof_fecha_hora, iof_estado "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN oferta_freelancer of ON iof.iof_oferta_freelancer = of.ofr_id "
				+ "WHERE iof.iof_estado IN (6, 5) AND iof.iof_estudiante = ? ";
		
		
		List<InscripcionOfertaFreelancer> listaInscripcion = jdbcTemplate.query(sql, new Object[]{idEstudiante}, new InscripcionOfertaFreelancerRowMapper());
		logger.debug("obtenerCategorias -- Saliendo de busqueda de las categorias ");
		
		return listaInscripcion;
		
	}


	@Override
	public JSONRespuesta listarPreInscripcionesOfertaFreelancer(int idFreelancer, String search, int start, int length,
			int draw, int posicion, String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "iof.iof_id", "o.ofe_titulo", "u.usu_email", "e.est_nombre" };
		
		int fin = start + length - 1;
		
		
		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN estado e ON e.est_id = iof.iof_estado "
				+ "INNER JOIN freelancer f ON f.free_id = of.ofr_freelancer "
				+ "WHERE iof.iof_estado IN (5, 6, 13)  ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND f.free_id = ? AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { idFreelancer ,"&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de Inscripcion Oferta Freelancers filtrados: " + sql);
		
			
		sql = "SELECT iof_id, ofe_titulo, usu_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "iof.iof_id, o.ofe_titulo, u.usu_email, e.est_nombre "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "INNER JOIN estudiante est ON est.estu_id = iof.iof_estudiante "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "INNER JOIN estado e ON e.est_id = iof.iof_estado "
				+ "INNER JOIN freelancer f ON f.free_id = of.ofr_freelancer "
				+ "WHERE iof.iof_estado IN (5, 6, 13)  AND f.free_id = ? "
				+ "AND (o.ofe_titulo LIKE ? OR u.usu_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de Inscripcion Oferta Freelancers filtrados:: " + sql);

		List<InscripcionOfertaFreelancer> listaInscripcionOfertaFreelancer = jdbcTemplate.query(sql, 
				new Object[] { idFreelancer ,"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<InscripcionOfertaFreelancer>() {

					public InscripcionOfertaFreelancer mapRow(ResultSet rs, int rowNum) throws SQLException {
																	
						InscripcionOfertaFreelancer inscripcionOfertaFreelancer = obtenerinscripcionOfertaFreelancer(rs.getInt("iof_id"));
						return inscripcionOfertaFreelancer;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);	
		respuesta.setData(listaInscripcionOfertaFreelancer);

		return respuesta;
		
	}


	@Override
	public Boolean rechazarInscripcionEstudiante(int idInscripcionOfertaFreelancer) {
		logger.debug("--- DENTRO DE rechazar la inscripcion del estudiante en la oferta freelancer--------");

		String SQL1 = "UPDATE inscripcion_oferta_freelancer SET iof_estado = ?, iof_confirmacion = ?  "
				+ "WHERE iof_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, Constants.ID_ESTADO_NO_INSCRITO, false,  idInscripcionOfertaFreelancer);

		if (resultado1 > 0) {
			logger.debug("--- realizado -----");
			return true;
		} else {
			logger.debug("No se pudo");
			return false;
		}
	}


	@Override
	public boolean eliminarInscripcionFreelancer(int idInscripcion) {
		logger.debug("---JB Dentro de eliminarInscripcionFreelancer");
		
		String sql = "UPDATE inscripcion_oferta_freelancer SET iof_estado=7" +
					 " WHERE iof_id=?";
		
		int resultado1 = jdbcTemplate.update(sql, idInscripcion);

		if (resultado1 > 0) {
			logger.debug("--- INSCRIPCION ELIMINADA -----");
			return true;
		} else {
			logger.debug("No se pudo ELIMINAR LA SUBSCRIPCION DEL FREELANCER");
			return false;
		}
	}


	



	
	

}
