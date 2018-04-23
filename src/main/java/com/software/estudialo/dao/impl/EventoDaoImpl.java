/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.EventoDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.PublicadorDao;
import com.software.estudialo.dao.TipoEventoDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.Publicador;
import com.software.estudialo.entities.TipoEvento;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.service.impl.StorageService;
import com.software.estudialo.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class EventoDaoImpl.
 *
 * @author LUIS
 */
@Repository("eventoDao")
public class EventoDaoImpl implements EventoDao{ 
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EventoDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;
	

	@Autowired
	InstitucionDao institucionDao;
	
	/** The tipo evento dao. */
	@Autowired
	TipoEventoDao tipoEventoDao;	
	
	@Autowired
	StorageService storageService;
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#obtenerEvento(int)
	 */
	@Override
	public Evento obtenerEvento(int id) {
		logger.debug("obtenerEvento -- Buscando evento por Id ");

		String SQL = "SELECT e.evt_id, e.evt_titulo, e.evt_descripcion, e.evt_tipo_evento, e.evt_fecha_evento, e.evt_estado, e.evt_imagen, e.evt_institucion "
				+ "FROM evento e WHERE e.evt_id = ?;";

		Evento eventoResponse = jdbcTemplate.query(SQL, new Object[] { id }, new EventoResultSetExtractor());		
		logger.debug("obtenerEvento -- Saliendo evento por Id");
		return eventoResponse;
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#listarEventos(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarEventos(String search, int start, int length, int draw, int posicion, String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ev.evt_id", "ev.evt_titulo", "ev.evt_descripcion", "te.tev_nombre", "ev.evt_fecha_evento", "e.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM evento ev "
				+ "INNER JOIN tipo_evento te ON te.tev_id = ev.evt_tipo_evento "
				+ "INNER JOIN estado e ON ev.evt_estado = e.est_id "
				+ "WHERE ev.evt_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (ev.evt_titulo LIKE ? OR ev.evt_descripcion LIKE ? ";
			sql = sql + "OR te.tev_nombre LIKE ? OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de eventos filtrados: " + sql);
		
			
		sql = "SELECT evt_id, evt_titulo, evt_descripcion, tev_nombre, evt_fecha_evento, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ev.evt_id, ev.evt_titulo, ev.evt_descripcion, te.tev_nombre, ev.evt_fecha_evento, e.est_nombre FROM evento ev "
				+ "INNER JOIN tipo_evento te ON te.tev_id = ev.evt_tipo_evento "
				+ "INNER JOIN estado e ON ev.evt_estado = e.est_id "
				+ "WHERE ev.evt_estado IN (8) "
				+ "AND (ev.evt_titulo LIKE ? OR ev.evt_descripcion LIKE ? OR te.tev_nombre LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Evento> listaEvento = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Evento>() {

					public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
						Evento evento = obtenerEvento(rs.getInt("evt_id"));
						return evento;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaEvento);

		return respuesta;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#agregarEvento(com.software.estudialo.entities.Evento)
	 */
	@Override
	public int agregarEvento(Evento evento) {
		
		logger.debug("--- AGREGANDO NUEVA EVENTO ------");

		try {

			String SQL1 = "INSERT INTO evento (evt_titulo, evt_descripcion, evt_institucion, evt_tipo_evento, evt_fecha_evento, evt_estado, evt_imagen) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
			
			Calendar cal = Calendar.getInstance();
			int year  = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day   = cal.get(Calendar.DAY_OF_MONTH);
			cal.clear();
			cal.set(year, month, day);
			java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());			
			
			//int idInstitucion = institucionDao.obtenerInstitucion(evento.getInstitucion().getId());
				
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado1 = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, evento.getTitulo());
					pstm.setString(2, evento.getDescripcion());
					pstm.setInt(3, evento.getInstitucion().getId());
					pstm.setInt(4, evento.getTipoEvento().getId());
					pstm.setDate(5, today);
					pstm.setInt(6, Constants.ID_ESTADO_ACTIVO_ENTIDADES_SECUNDARIAS);
					pstm.setString(7, "default.png");
					return pstm;
				}
			}, keyHolder);
			
			if (resultado1 > 0) {
				logger.debug("Se inserto el evento");
				
				int key = 0;

				if (keyHolder.getKeys().size() == 1) {
					key = keyHolder.getKey().intValue();
				} else if (keyHolder.getKeys().size() > 1) {
					logger.debug("------- Se encuentra mas de una llave ----");
					key = Integer.parseInt(String.valueOf(keyHolder.getKeys().get("evt_id")));
				} else {
					logger.debug("------- No se pudo extraer la llave ----");
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}
							
								
				return key;
			} else {
				throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
			}
				
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
		}
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#modificarEvento(int, com.software.estudialo.entities.Evento)
	 */
	@Override
	public boolean modificarEvento(int id, Evento evento) {
		
		logger.debug("--- DENTRO DE MODIFICAR evento --------");

		String SQL1 = "UPDATE evento SET evt_titulo = ?, evt_descripcion = ?, evt_institucion = ?, evt_tipo_evento = ?, evt_fecha_evento = ?, evt_estado = ? WHERE evt_id = ?;";

		//int idPublicador = publicadorDao.obtenerIdPublicadorConIdUsuario(evento.getInstitucion().getId());
		
		int resultado1 = jdbcTemplate.update(SQL1, evento.getTitulo(), evento.getDescripcion(), evento.getInstitucion().getId(), 
				evento.getTipoEvento().getId(), evento.getFecha(), evento.getEstado().getId(), evento.getId());

		if (resultado1 > 0) {
			logger.debug("--- evento MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el evento");
			return false;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#eliminarEvento(int)
	 */
	@Override
	public boolean eliminarEvento(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR evento --------");

		int idEliminado = 7;

		String SQL1 = "UPDATE evento SET evt_estado = ? WHERE evt_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, idEliminado, id);

		if (resultado1 > 0) {
			logger.debug("--- evento CAMBIADO DE ESTADO A ELIMINADO ------");
			return true;
		} else {
			return false;
		}
		
	}	
	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#buscarEvento(int)
	 */
	@Override
	public Boolean buscarEvento(int id) {
		logger.debug("------- BUSCANDO EXISTENCIA DE EVENTO ------");

		String sql = "SELECT COUNT(*) as cant FROM evento evt WHERE evt.evt_id = ? AND evt.evt_estado IN (8, 9)";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}


	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#buscarEvento(com.software.estudialo.entities.Evento)
	 */
	@Override
	public Boolean buscarEvento(Evento evento) {
		logger.debug("------- Buscando evento ");

		String sql = "SELECT COUNT(*) from evento evt "
				+ "WHERE evt_estado IN (8, 9) AND (evt.evt_titulo = ? OR evt.evt_descripcion = ?);";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { evento.getTitulo(), evento.getDescripcion() },
				Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#listarEventoBuscador(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarEventoBuscador(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		logger.debug(" listarEventoBuscador ---- listar eventos buscador");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ev.evt_id", "ev.evt_titulo", "ev.evt_descripcion", "tev.tev_nombre", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM evento ev "
				+ "INNER JOIN tipo_evento tev ON ev.evt_tipo_evento = tev.tev_id "			
				+ "INNER JOIN estado est ON ev.evt_estado = est.est_id "				
				+ "WHERE ev.evt_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de oferta =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND ( ev.evt_titulo LIKE ? OR ev.evt_descripcion LIKE ? OR tev.tev_nombre LIKE ?) ";			
					

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println(" 1 ::: Consulta realizada para los eventos: " + sql);
		
			
		sql = "SELECT evt_id, evt_titulo, evt_descripcion, tev_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ev.evt_id, ev.evt_titulo, ev.evt_descripcion, tev.tev_nombre, est.est_nombre FROM evento ev "
				+ "INNER JOIN tipo_evento tev ON ev.evt_tipo_evento = tev.tev_id "			
				+ "INNER JOIN estado est ON ev.evt_estado = est.est_id "			
				+ "WHERE ev.evt_estado IN (8) "
				+ "AND (ev.evt_titulo LIKE ? OR ev.evt_descripcion LIKE ? OR tev.tev_nombre LIKE ? )) ";	
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println(" 2 ::: Consulta realizada para las ofertas: " + sql);

		List<Evento> listaEvento = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Evento>() {					

					public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("mapRow  ----- obteniendo eventos");
						Evento evento = obtenerEvento(rs.getInt("evt_id"));
						return evento;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaEvento);
		return respuesta;
		
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EventoDao#listarEventoFiltros(int, int, int, int, java.lang.String, int)
	 */
	@Override
	public JSONRespuesta listarEventoFiltros(int start, int length, int draw, int posicion, String direccion,
			int tipoEvento) {
		
		logger.debug(" listarEventoFiltros ---- listar evento filtros");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ev.evt_id", "ev.evt_titulo", "ev.evt_descripcion", "tev.tev_nombre", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM evento ev "
				+ "INNER JOIN tipo_evento tev ON ev.evt_tipo_evento = tev.tev_id "			
				+ "INNER JOIN estado est ON ev.evt_estado = est.est_id "				
				+ "WHERE ev.evt_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de oferta =" + count);
		int filtrados = count;
		
		ArrayList<Object> parametros = new ArrayList<Object>();

		if (tipoEvento != 0) {
			sql = sql + "AND ev.evt_tipo_evento = ? ";	
			parametros.add(tipoEvento);
		}
		
		filtrados = jdbcTemplate.queryForObject(sql, parametros.toArray(), Integer.class);

		System.out.println(" 1 ::: Consulta realizada para las ofertas: " + sql);
		
			
		String sql2 = "SELECT evt_id, evt_titulo, evt_descripcion, tev_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ev.evt_id, ev.evt_titulo, ev.evt_descripcion, tev.tev_nombre, est.est_nombre FROM evento ev "
				+ "INNER JOIN tipo_evento tev ON ev.evt_tipo_evento = tev.tev_id "			
				+ "INNER JOIN estado est ON ev.evt_estado = est.est_id "			
				+ "WHERE ev.evt_estado IN (8) ";		
		
		ArrayList<Object> parametros2 = new ArrayList<Object>();	
		
		if (tipoEvento != 0) {
			sql2 = sql2 + "AND ev.evt_tipo_evento = ? ";	
			parametros2.add(tipoEvento);
		} 
		
		parametros2.add(start);
		parametros2.add(fin);
								
		sql2 = sql2 + ") as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println(" 2 ::: Consulta realizada para las ofertas: " + sql2);

		List<Evento> listaEvento = jdbcTemplate.query(sql2, parametros2.toArray(),
				new RowMapper<Evento>() {					

					public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("mapRow  ----- obteniendo eventos");
						Evento evento = obtenerEvento(rs.getInt("evt_id"));
						return evento;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaEvento);
		return respuesta;
		
		
	}	
	
	
	
	/**
	 * The Class EventoResultSetExtractor.
	 */
	class EventoResultSetExtractor implements ResultSetExtractor<Evento>{		
		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Evento extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("OfertaResultSetExtractor -- Obteniendo oferta");
				Evento evento = new Evento();
				evento.setId(rs.getInt("evt_id"));
				evento.setTitulo(rs.getString("evt_titulo"));
				evento.setDescripcion(rs.getString("evt_descripcion"));
				evento.setFecha(rs.getDate("evt_fecha_evento"));
				evento.setImagenUrl(rs.getString("evt_imagen"));
				
								
				Institucion institucion = institucionDao.obtenerInstitucion(rs.getInt("evt_institucion"));
				TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(rs.getInt("evt_tipo_evento"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("evt_estado"));	
				
				evento.setInstitucion(institucion);
				evento.setTipoEvento(tipoEvento);
				evento.setEstado(estado);
				   		
	    		return evento;
			}
			return null;
		}		
		
	}



	@Override
	public boolean modificarImagenEvento(int idEvento, String newFileName) {
	
		logger.debug("--- DENTRO DE MODIFICAR IMAGEN EVENTO --------");

		String SQL1 = "UPDATE evento SET evt_imagen = ? WHERE evt_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, newFileName, idEvento);

		if (resultado1 > 0) {
			logger.debug("--- IMAGEN MODIFICADA ------");
			return true;

		} else {
			return false;
		}
		
	}


	@Override
	public JSONRespuesta listarEventosAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "ev.evt_id", "ev.evt_titulo", "ev.evt_descripcion", "te.tev_nombre", "ev.evt_fecha_evento", "e.est_nombre" };

		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM evento ev "
				+ "INNER JOIN tipo_evento te ON te.tev_id = ev.evt_tipo_evento "
				+ "INNER JOIN estado e ON ev.evt_estado = e.est_id "
				+ "WHERE ev.evt_estado IN (8, 9) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (ev.evt_titulo LIKE ? OR ev.evt_descripcion LIKE ? ";
			sql = sql + "OR te.tev_nombre LIKE ? OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de eventos filtrados: " + sql);
		
			
		sql = "SELECT evt_id, evt_titulo, evt_descripcion, tev_nombre, evt_fecha_evento, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ev.evt_id, ev.evt_titulo, ev.evt_descripcion, te.tev_nombre, ev.evt_fecha_evento, e.est_nombre FROM evento ev "
				+ "INNER JOIN tipo_evento te ON te.tev_id = ev.evt_tipo_evento "
				+ "INNER JOIN estado e ON ev.evt_estado = e.est_id "
				+ "WHERE ev.evt_estado IN (8, 9) "
				+ "AND (ev.evt_titulo LIKE ? OR ev.evt_descripcion LIKE ? OR te.tev_nombre LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Evento> listaEvento = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Evento>() {

					public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
						Evento evento = obtenerEvento(rs.getInt("evt_id"));
						return evento;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaEvento);

		return respuesta;
		
	}


	@Override
	public List<Evento> listarEventosInstitucion(int idInstitucion) {
		logger.debug("listarEventosInstitucion -- Buscando  ");
		
		String sql = "SELECT e.evt_id, e.evt_titulo, e.evt_descripcion, e.evt_tipo_evento, e.evt_fecha_evento, e.evt_estado, e.evt_imagen, e.evt_institucion "
				+ "FROM evento e WHERE e.evt_institucion = ? AND e.evt_estado IN (8, 9);";
		List<Evento> listaEventos = jdbcTemplate.query(sql, new Object[] {idInstitucion}, new EventoRowMapper());
		logger.debug("listarEventosInstitucion -- Saliendo  ");
		
		return listaEventos;
	}
	
	
	
	class EventoRowMapper implements RowMapper<Evento>{
	    
    	/* (non-Javadoc)
    	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
    	 */
    	@Override
	    public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Evento evento = new Evento();
			evento.setId(rs.getInt("evt_id"));
			evento.setTitulo(rs.getString("evt_titulo"));
			evento.setDescripcion(rs.getString("evt_descripcion"));
			evento.setFecha(rs.getDate("evt_fecha_evento"));
			evento.setImagenUrl(rs.getString("evt_imagen"));
			
							
			Institucion institucion = institucionDao.obtenerInstitucion(rs.getInt("evt_institucion"));
			TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(rs.getInt("evt_tipo_evento"));
			Estado estado = estadoDao.obtenerEstado(rs.getInt("evt_estado"));	
			
			evento.setInstitucion(institucion);
			evento.setTipoEvento(tipoEvento);
			evento.setEstado(estado);
			return evento;
	        
	    }
	}

}
