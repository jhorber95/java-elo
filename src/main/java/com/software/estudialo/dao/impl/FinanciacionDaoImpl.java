/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.FinanciacionDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Financiacion;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
// TODO: Auto-generated Javadoc
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.TipoOfrece;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.util.Constants;

/**
 * The Class FinanciacionDaoImpl.
 *
 * @author LUIS
 */
@Repository("financiacionDao")
public class FinanciacionDaoImpl implements FinanciacionDao {

	/** The logger. */
	private Logger logger = Logger.getLogger(FinanciacionDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;

	/** The institucion dao. */
	@Autowired
	InstitucionDao institucionDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.FinanciacionDao#obtenerFinanciacion(int)
	 */
	@Override
	public Financiacion obtenerFinanciacion(int id) {
		logger.debug("obtenerFinanciacion -- Buscando financiacion por Id ");

		String SQL = "SELECT f.fin_id, f.fin_informacion, f.fin_institucion, f.fin_estado "
				+ "FROM financiacion f WHERE f.fin_id = ?;";

		Financiacion financiacionResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new FinanciacionResultSetExtractor());
		logger.debug("obtenerFinanciacion -- Saliendo financiacion por Id");
		return financiacionResponse;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FinanciacionDao#listarFinanciaciones(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarFinanciaciones(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "f.fin_id", "f.fin_informacion", "i.inst_nombre", "e.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM financiacion f "
				+ "INNER JOIN institucion i ON i.inst_id = f.fin_institucion "
				+ "INNER JOIN estado e ON f.fin_estado = e.est_id "
				+ "WHERE f.fin_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (f.fin_informacion LIKE ? OR i.inst_nombre LIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de financiaciones filtrados: " + sql);
		
			
		sql = "SELECT fin_id, fin_informacion, inst_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "f.fin_id, f.fin_informacion, i.inst_nombre, e.est_nombre FROM financiacion f "
				+ "INNER JOIN institucion i ON i.inst_id = f.fin_institucion "
				+ "INNER JOIN estado e ON f.fin_estado = e.est_id "
				+ "WHERE f.fin_estado IN (8) "
				+ "AND (f.fin_informacion LIKE ? OR i.inst_nombre LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Financiacion> listaFinanciacion = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Financiacion>() {

					public Financiacion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Financiacion financiacion = obtenerFinanciacion(rs.getInt("fin_id"));
						return financiacion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaFinanciacion);

		return respuesta;
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FinanciacionDao#agregarFinanciacion(com.software.estudialo.entities.Financiacion)
	 */
	@Override
	public boolean agregarFinanciacion(Financiacion financiacion) { 

		logger.debug("--- AGREGANDO NUEVA FINANCIACION ------");

		try {

			String SQL1 = "INSERT INTO financiacion (fin_informacion, fin_institucion, fin_estado) VALUES (?, ?, ?);";

			int resultado1 = jdbcTemplate.update(SQL1, financiacion.getInformacion(),
					financiacion.getInstitucion().getId(), financiacion.getEstado().getId());

			if (resultado1 > 0) {
				logger.debug("Se inserto el financiacion");
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
	 * @see com.software.estudialo.dao.FinanciacionDao#modificarFinanciacion(int, com.software.estudialo.entities.Financiacion)
	 */
	@Override
	public boolean modificarFinanciacion(int id, Financiacion financiacion) {

		logger.debug("--- DENTRO DE MODIFICAR financiacion --------");

		String SQL1 = "UPDATE financiacion SET fin_informacion = ?, fin_institucion = ?, fin_estado = ? WHERE fin_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, financiacion.getInformacion(), financiacion.getInstitucion().getId(),
				financiacion.getEstado().getId(), id);

		if (resultado1 > 0) {
			logger.debug("--- financiacion MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el financiacion");
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FinanciacionDao#eliminarFinanciacion(int)
	 */
	@Override
	public boolean eliminarFinanciacion(int id) {
		logger.debug("--- DENTRO DE ELIMINAR financiacion --------");

		int idEliminado = 7;

		String SQL1 = "UPDATE financiacion SET fin_estado = ? WHERE fin_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, idEliminado, id);

		if (resultado1 > 0) {
			logger.debug("--- financiacion CAMBIADO DE ESTADO A ELIMINADO ------");
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FinanciacionDao#buscarFinanciacion(int)
	 */
	@Override
	public Boolean buscarFinanciacion(int id) {
		logger.debug("------- BUSCANDO EXISTENCIA DE FINANCIACION ------");

		String sql = "SELECT COUNT(*) as cant FROM financiacion fin WHERE fin.fin_id = ? AND fin.fin_estado IN (8, 9)";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.FinanciacionDao#buscarFinanciacion(com.software.estudialo.entities.Financiacion)
	 */
	@Override
	public Boolean buscarFinanciacion(Financiacion financiacion) {
		logger.debug("------- Buscando financiacion ");

		String sql = "SELECT COUNT(*) from financiacion fin "
				+ "WHERE fin_estado IN (8, 9) AND fin.fin_institucion = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { financiacion.getInstitucion().getId() },
				Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.dao.FinanciacionDao#listarFinanciacionBuscador(
	 * java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarFinanciacionBuscador(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug(" listarFinanciacionBuscador ---- listar financiacions buscador");

		JSONRespuesta respuesta = new JSONRespuesta();

		String[] campos = { "f.fin_id", "f.fin_informacion", "i.inst_nombre", "tev.tev_nombre", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM financiacion f " + "INNER JOIN institucion i ON i.inst_id = f.fin_institucion "
				+ "INNER JOIN estado est ON f.fin_estado = est.est_id " + "WHERE f.fin_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de financiaciones =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND ( f.fin_informacion ILIKE ? OR i.inst_nombre ILIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		System.out.println(" 1 ::: Consulta realizada para los financiacions: " + sql);

		sql = "SELECT fin_id, fin_informacion, fin_institucion, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "f.fin_id, f.fin_informacion, f.fin_institucion, est.est_nombre FROM financiacion f "
				+ "INNER JOIN institucion i ON i.inst_id = f.fin_institucion "
				+ "INNER JOIN estado est ON f.fin_estado = est.est_id " + "WHERE f.fin_estado IN (8) "
				+ "AND (f.fin_informacion ILIKE ? OR i.inst_nombre ILIKE ? )) ";
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";

		System.out.println(" 2 ::: Consulta realizada para las financiaciones: " + sql);

		final List<Financiacion> listaFinanciacion = new ArrayList<>();
		
		
		jdbcTemplate.query(sql, new Object[] { "%" + search + "%", "%" + search + "%", start, fin },
				new RowCallbackHandler() {
			
		      @Override
		      public void processRow(ResultSet rs) throws SQLException {
		    	  
		    	  logger.debug("mapRow  ----- obteniendo ofertas");
					Financiacion financiacion = obtenerFinanciacion(rs.getInt("fin_id"));
					
					Institucion institucion = financiacion.getInstitucion();
					Boolean ofrecedorActivo = false;
					
					//Verificacion ofrecedor activo
					if (institucion.getEstado().getId() == Constants.ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS) {
						ofrecedorActivo = true;
					} 
					
					if (ofrecedorActivo) {
						listaFinanciacion.add(financiacion);
					}				    	
		      }
		}); 

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaFinanciacion);
		return respuesta;
	}

	/**
	 * The Class FinanciacionResultSetExtractor.
	 */
	class FinanciacionResultSetExtractor implements ResultSetExtractor<Financiacion> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Financiacion extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("Financiacion ResultSetExtractor -- Obteniendo Financiacion");
				Financiacion financiacion = new Financiacion();
				financiacion.setId(rs.getInt("fin_id"));
				financiacion.setInformacion(rs.getString("fin_informacion"));

				Institucion institucion = institucionDao.obtenerInstitucion(rs.getInt("fin_institucion"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("fin_estado"));

				financiacion.setInstitucion(institucion);
				financiacion.setEstado(estado);

				return financiacion;
			}
			return null;
		}

	}

	@Override
	public Financiacion obtenerFinanciacionPorInstitucion(int idInstitucion) {
		logger.debug("obtenerFinanciacionPorInstitucion -- Buscando financiacion por Id institucion ");

		String SQL = "SELECT f.fin_id, f.fin_informacion, f.fin_institucion, f.fin_estado "
				+ "FROM financiacion f WHERE f.fin_institucion = ? AND f.fin_estado = 8;";

		Financiacion financiacionResponse = jdbcTemplate.query(SQL, new Object[] { idInstitucion },
				new FinanciacionResultSetExtractor());
		logger.debug("obtenerFinanciacionPorInstitucion -- Saliendo financiacion por Id insittucion");
		return financiacionResponse;
	}



	@Override
	public JSONRespuesta listarFinanciacionesAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
	JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "f.fin_id", "f.fin_informacion", "i.inst_nombre", "e.est_nombre" };

		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM financiacion f "
				+ "INNER JOIN institucion i ON i.inst_id = f.fin_institucion "
				+ "INNER JOIN estado e ON f.fin_estado = e.est_id "
				+ "WHERE f.fin_estado IN (8, 9) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (f.fin_informacion ILIKE ? OR i.inst_nombre ILIKE ? ";
			sql = sql + "OR e.est_nombre ILIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de financiaciones filtrados: " + sql);
		
			
		sql = "SELECT fin_id, fin_informacion, inst_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "f.fin_id, f.fin_informacion, i.inst_nombre, e.est_nombre FROM financiacion f "
				+ "INNER JOIN institucion i ON i.inst_id = f.fin_institucion "
				+ "INNER JOIN estado e ON f.fin_estado = e.est_id "
				+ "WHERE f.fin_estado IN (8, 9) "
				+ "AND (f.fin_informacion ILIKE ? OR i.inst_nombre ILIKE ? OR e.est_nombre ILIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Financiacion> listaFinanciacion = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Financiacion>() {

					public Financiacion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Financiacion financiacion = obtenerFinanciacion(rs.getInt("fin_id"));
						return financiacion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaFinanciacion);

		return respuesta;
	}



}
