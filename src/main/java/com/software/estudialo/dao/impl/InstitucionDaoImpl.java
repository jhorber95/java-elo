/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.TipoInstitucionDao;
import com.software.estudialo.dao.impl.UsuarioDaoImpl.UsuarioCompleteResultSetExtractor;
import com.software.estudialo.dao.impl.UsuarioDaoImpl.UsuarioCompleteSinPasswordConRolesResultSetExtractor;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Genero;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.entities.TipoIdentificacion;
import com.software.estudialo.entities.TipoInstitucion;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.util.Constants;
import com.software.estudialo.util.Encriptar;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitucionDaoImpl.
 *
 * @author LUIS
 */
@Repository("institucionDao")
public class InstitucionDaoImpl implements InstitucionDao{

	/** The logger. */
	private Logger logger = Logger.getLogger(InstitucionDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** The tipo institucion dao. */
	@Autowired
	TipoInstitucionDao tipoInstitucionDao;
	
	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao; 
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#obtenerInstitucion(int)
	 */
	@Override
	public Institucion obtenerInstitucion(int id) {
		logger.debug("obtenerInstitucion -- Buscando institucion por Id ");

		String SQL = "SELECT i.inst_id, i.inst_nombre, i.inst_nit, i.inst_latitud, i.inst_longitud, i.inst_direccion, i.inst_telefono, "
				+ "i.inst_url, i.inst_descripcion, i.inst_email, i.inst_tipo_institucion, i.inst_estado, i.inst_imagen FROM institucion i WHERE i.inst_id = ?;";

		Institucion institucionResponse = jdbcTemplate.query(SQL, new Object[] { id },
				new InstitucionResultSetExtractor());
		
		System.out.println("obtenerInstitucion -- Saliendo institucion por Id");
		return institucionResponse;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#listarInstituciones(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarInstituciones(String search, int start, int length, int draw, int posicion,
			String direccion) {

				
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "i.inst_id", "i.inst_nombre", "i.inst_email", "ti.tins_nombre", "e.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM institucion i "
				+ "INNER JOIN tipo_institucion ti ON ti.tins_id = i.inst_tipo_institucion "
				+ "INNER JOIN estado e ON i.inst_estado = e.est_id "
				+ "WHERE i.inst_estado IN (1) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (i.inst_nombre LIKE ? OR i.inst_email LIKE ? ";
			sql = sql + "OR ti.tins_nombre LIKE ? OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de instituciones filtrados: " + sql);
		
			
		sql = "SELECT inst_id, inst_nombre, inst_email, tins_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "i.inst_id, i.inst_nombre, i.inst_email, ti.tins_nombre, e.est_nombre FROM institucion i "
				+ "INNER JOIN tipo_institucion ti ON ti.tins_id = i.inst_tipo_institucion "
				+ "INNER JOIN estado e ON i.inst_estado = e.est_id "
				+ "WHERE i.inst_estado IN (1) "
				+ "AND (i.inst_nombre LIKE ? OR i.inst_email LIKE ? OR ti.tins_nombre LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Institucion> listaInstitucion = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Institucion>() {

					public Institucion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Institucion institucion = obtenerInstitucion(rs.getInt("inst_id"));
						return institucion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaInstitucion);

		return respuesta;
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#agregarInstitucion(com.software.estudialo.entities.Institucion)
	 */
	@Override
	public boolean agregarInstitucion(Institucion institucion) {
		logger.debug("--- AGREGANDO NUEVA INSTITUCION ------");

		try {

			String SQL1 = "INSERT INTO institucion (inst_nombre, inst_nit, inst_latitud, inst_longitud, inst_direccion, inst_telefono, inst_url, inst_descripcion, inst_email, "
					+ "inst_password, inst_tipo_institucion, inst_estado, inst_imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			
			String encryptada = Encriptar.toSHA256PasswordEncoder(institucion.getPassword());
			
			int resultado1 = jdbcTemplate.update(SQL1, institucion.getNombre(), institucion.getNit(), institucion.getLatitud(), institucion.getLongitud(), 
					institucion.getDireccion(), institucion.getTelefono(), institucion.getUrl(), institucion.getDescripcion(), institucion.getEmail(), 
					encryptada, institucion.getTipoInstitucion().getId(), Constants.ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS, Constants.DEFECTO_IMAGE);

			if (resultado1 > 0) {
				logger.debug("Se inserto el institucion");
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
	 * @see com.software.estudialo.dao.InstitucionDao#modificarInstitucion(int, com.software.estudialo.entities.Institucion)
	 */
	@Override
	public boolean modificarInstitucion(int id, Institucion institucion) {
		logger.debug("--- DENTRO DE MODIFICAR institucion por la institucion--------");

		String SQL1 = "UPDATE institucion SET inst_nombre = ?, inst_nit = ?, inst_latitud = ?, inst_longitud = ?, inst_direccion = ?, inst_telefono = ?, inst_url = ?, inst_descripcion = ?, "
				+ "inst_email = ?, inst_tipo_institucion = ? WHERE inst_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, institucion.getNombre(), institucion.getNit(), institucion.getLatitud(), institucion.getLongitud(), 
				institucion.getDireccion(), institucion.getTelefono(), institucion.getUrl(), institucion.getDescripcion(), institucion.getEmail(), 
				institucion.getTipoInstitucion().getId(), institucion.getId());

		if (resultado1 > 0) {
			logger.debug("--- institucion MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el institucion");
			return false;
		}
	}
	
	@Override
	public boolean modificarInstitucionAdmin(int id, Institucion institucion) {
		logger.debug("--- DENTRO DE MODIFICAR institucion desde el admin--------");

		String SQL1 = "UPDATE institucion SET inst_nombre = ?, inst_nit = ?, inst_latitud = ?, inst_longitud = ?, inst_direccion = ?, inst_telefono = ?, inst_url = ?, inst_descripcion = ?, "
				+ "inst_email = ?, inst_estado = ?, inst_tipo_institucion = ? WHERE inst_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, institucion.getNombre(), institucion.getNit(), institucion.getLatitud(), institucion.getLongitud(), 
				institucion.getDireccion(), institucion.getTelefono(), institucion.getUrl(), institucion.getDescripcion(), institucion.getEmail(), 
				institucion.getEstado().getId(), institucion.getTipoInstitucion().getId(), institucion.getId());

		if (resultado1 > 0) {
			logger.debug("--- institucion MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el institucion");
			return false;
		}
	}



	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#eliminarInstitucion(int)
	 */
	@Override
	public boolean eliminarInstitucion(int id) {
		logger.debug("--- DENTRO DE ELIMINAR institucion --------");

		int idEliminado = 7;

		String SQL1 = "UPDATE institucion SET inst_estado = ? WHERE inst_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, idEliminado, id);

		if (resultado1 > 0) {
			logger.debug("--- institucion CAMBIADO DE ESTADO A ELIMINADO ------");
			return true;
		} else {
			return false;
		}
	}




	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#buscarInstitucion(int)
	 */
	@Override
	public Boolean buscarInstitucion(int id) {
		logger.debug("------- BUSCANDO EXISTENCIA DE INSTITUCION ------");

		String sql = "SELECT COUNT(*) as cant FROM institucion i WHERE i.inst_id = ? AND i.inst_estado IN (1, 2)";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}



	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#buscarInstitucion(com.software.estudialo.entities.Institucion)
	 */
	@Override
	public Boolean buscarInstitucion(Institucion institucion) {
		logger.debug("------- Buscando institucion ");

		String sql = "SELECT COUNT(*) from institucion i "
				+ "WHERE i.inst_estado IN (1, 2) AND i.inst_nombre = ? OR i.inst_nit = ? OR (i.inst_direccion = ? OR i.inst_telefono = ? OR i.inst_url = ? OR i.inst_email = ?);";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { institucion.getNombre(), institucion.getNit(), institucion.getDireccion(), institucion.getTelefono(), institucion.getUrl(), 
						institucion.getEmail()}, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#obtenerInstitucionPorOferta(int)
	 */
	@Override
	public Institucion obtenerInstitucionPorOferta(int idOferta) {
		
		logger.debug("obtenerInstitucion -- Buscando institucion por Id ");

		String SQL = "SELECT i.inst_id, i.inst_nombre, i.inst_nit, i.inst_latitud, i.inst_longitud, i.inst_direccion, i.inst_telefono, "
				+ "i.inst_url, i.inst_descripcion, i.inst_email, i.inst_tipo_institucion, i.inst_estado, i.inst_imagen FROM institucion i "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_institucion = i.inst_id "
				+ "WHERE oi.oins_oferta = ?";

		Institucion institucionResponse = jdbcTemplate.query(SQL, new Object[] { idOferta },
				new InstitucionResultSetExtractor());
		
		System.out.println("obtenerInstitucion -- Saliendo institucion por Id");
		return institucionResponse;		
	}
	
	
	
	
	/**
	 * The Class InstitucionResultSetExtractor.
	 */
	class InstitucionResultSetExtractor implements ResultSetExtractor<Institucion>{

	
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Institucion extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("InstitucionResultSetExtractor -- Obteniendo institucion");
				Institucion institucionRow = new Institucion();
				institucionRow.setId(rs.getInt("inst_id"));
				institucionRow.setNombre(rs.getString("inst_nombre"));
				institucionRow.setNit(rs.getString("inst_nit"));
				institucionRow.setLatitud(rs.getString("inst_latitud"));
				institucionRow.setLongitud(rs.getString("inst_longitud"));
				institucionRow.setDireccion(rs.getString("inst_direccion"));
				institucionRow.setTelefono(rs.getString("inst_telefono"));
				institucionRow.setUrl(rs.getString("inst_url"));
				institucionRow.setDescripcion(rs.getString("inst_descripcion"));
				institucionRow.setEmail(rs.getString("inst_email"));
				institucionRow.setUrlImagen(rs.getString("inst_imagen"));
				
				TipoInstitucion tipoInstitucion = tipoInstitucionDao.obtenerTipoInstitucion(rs.getInt("inst_tipo_institucion"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("inst_estado"));
				institucionRow.setTipoInstitucion(tipoInstitucion);
				institucionRow.setEstado(estado);
				return institucionRow;
			}
			return null;
		}
		
	}




	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.InstitucionDao#obtenerIdInstitucionPorIdOferta(int)
	 */
	@Override
	public int obtenerIdInstitucionPorIdOferta(int idOferta) {
		logger.debug("------- Buscando al id institucion por el id oferta");
		
		String sql = "SELECT i.inst_id "
				+ "FROM institucion i "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_institucion = i.inst_id "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "WHERE o.ofe_id = ?; ";

		int idPublicador = jdbcTemplate.queryForObject(sql, new Object[]{idOferta}, Integer.class);
						
		return idPublicador;
	}



	@Override
	public boolean modificarImagenInstitucion(int idInstitucion, String newFileName) {
		
		logger.debug("--- DENTRO DE MODIFICAR IMAGEN INSTITUCION --------");

		String SQL1 = "UPDATE institucion SET inst_imagen = ? WHERE inst_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, newFileName, idInstitucion);

		if (resultado1 > 0) {
			logger.debug("--- IMAGEN MODIFICADA ------");
			return true;

		} else {
			return false;
		}
		
	}



	@Override
	public JSONRespuesta listarInstitucionesFinancieras(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "i.inst_id", "i.inst_nombre", "i.inst_email", "e.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM institucion i "				
				+ "INNER JOIN estado e ON i.inst_estado = e.est_id "
				+ "WHERE i.inst_estado IN (1) AND i.inst_tipo_institucion = 4 ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (i.inst_nombre ILIKE ? OR i.inst_email ILIKE ? ";
			sql = sql + "OR e.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de instituciones filtrados: " + sql);
		
			
		sql = "SELECT inst_id, inst_nombre, inst_email, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "i.inst_id, i.inst_nombre, i.inst_email, e.est_nombre "
				+ "FROM institucion i "
				+ "INNER JOIN estado e ON i.inst_estado = e.est_id "
				+ "WHERE i.inst_estado IN (1) AND i.inst_tipo_institucion = 4 "
				+ "AND (i.inst_nombre LIKE ? OR i.inst_email LIKE ? OR e.est_nombre LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Institucion> listaInstitucion = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Institucion>() {

					public Institucion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Institucion institucion = obtenerInstitucion(rs.getInt("inst_id"));
						return institucion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaInstitucion);

		return respuesta;
		
		
	}



	@Override
	public Boolean buscarExisteInstitucionPorUsername(String username) {
		logger.debug("------- Buscando institucion ");

		String sql = "SELECT COUNT(*) "
				+ "FROM institucion i "
				+ "WHERE i.inst_email = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { username }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}	}



	@Override
	public Institucion buscarInstitucionPorUsername(String username) {
		logger.debug("buscarInstitucionPorUsername -- Buscando institucion por email ");

		String SQL = "SELECT i.inst_id, i.inst_nombre, i.inst_nit, i.inst_latitud, i.inst_longitud, i.inst_direccion, i.inst_telefono, "
				+ "i.inst_url, i.inst_descripcion, i.inst_email, i.inst_password, i.inst_tipo_institucion, i.inst_estado, i.inst_imagen "
				+ "FROM institucion i "
				+ "WHERE i.inst_email = ?;";

		Institucion institucionResponse = jdbcTemplate.query(SQL, new Object[] { username }, new InstitucionCompleteResultSetExtractor());

		System.out.println("obtenerUsuario -- Saliendo usuario por Id");
		return institucionResponse;
	}
	
	
	class InstitucionCompleteResultSetExtractor implements ResultSetExtractor<Institucion> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Institucion extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("InstitucionCompleteResultSetExtractor -- Obteniendo insittucion");
				Institucion institucionRow = new Institucion();
				institucionRow.setId(rs.getInt("inst_id"));
				institucionRow.setNombre(rs.getString("inst_nombre"));
				institucionRow.setNit(rs.getString("inst_nit"));
				institucionRow.setLatitud(rs.getString("inst_latitud"));
				institucionRow.setLongitud(rs.getString("inst_longitud"));
				institucionRow.setDireccion(rs.getString("inst_direccion"));
				institucionRow.setTelefono(rs.getString("inst_telefono"));
				institucionRow.setUrl(rs.getString("inst_url"));
				institucionRow.setDescripcion(rs.getString("inst_descripcion"));
				institucionRow.setEmail(rs.getString("inst_email"));
				institucionRow.setPassword(rs.getString("inst_password"));
				institucionRow.setUrlImagen(rs.getString("inst_imagen"));
				
				TipoInstitucion tipoInstitucion = tipoInstitucionDao.obtenerTipoInstitucion(rs.getInt("inst_tipo_institucion"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("inst_estado"));
				institucionRow.setTipoInstitucion(tipoInstitucion);
				institucionRow.setEstado(estado);
				return institucionRow;
			}
			return null;
		}

	}




	@Override
	public Institucion obtenerInstitucionPorUsernameCompletoSinPassword(String username) {
		logger.debug("obtenerUsuarioPorUsernameCompletoSinPassword -- Buscando usuario por username (email) pero sin password, pero con roles ");

		String SQL = "SELECT i.inst_id, i.inst_nombre, i.inst_nit, i.inst_latitud, i.inst_longitud, i.inst_direccion, i.inst_telefono, "
				+ "i.inst_url, i.inst_descripcion, i.inst_email, i.inst_tipo_institucion, i.inst_estado, i.inst_imagen "
				+ "FROM institucion i "
				+ "WHERE i.inst_email = ?;";

		Institucion institucionResponse = jdbcTemplate.query(SQL, new Object[] { username }, new InstitucionCompleteSinPasswordConRolesResultSetExtractor());

		System.out.println("obtenerInstitucion -- Saliendo institucion por Id");
		return institucionResponse;
	}

	
	class InstitucionCompleteSinPasswordConRolesResultSetExtractor implements ResultSetExtractor<Institucion> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Institucion extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("UsuarioResultSetExtractor -- Obteniendo usuario");
				Institucion institucionRow = new Institucion();
				institucionRow.setId(rs.getInt("inst_id"));
				institucionRow.setNombre(rs.getString("inst_nombre"));
				institucionRow.setNit(rs.getString("inst_nit"));
				institucionRow.setLatitud(rs.getString("inst_latitud"));
				institucionRow.setLongitud(rs.getString("inst_longitud"));
				institucionRow.setDireccion(rs.getString("inst_direccion"));
				institucionRow.setTelefono(rs.getString("inst_telefono"));
				institucionRow.setUrl(rs.getString("inst_url"));
				institucionRow.setDescripcion(rs.getString("inst_descripcion"));
				institucionRow.setEmail(rs.getString("inst_email"));
				institucionRow.setUrlImagen(rs.getString("inst_imagen"));
				
				TipoInstitucion tipoInstitucion = tipoInstitucionDao.obtenerTipoInstitucion(rs.getInt("inst_tipo_institucion"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("inst_estado"));
				institucionRow.setTipoInstitucion(tipoInstitucion);
				institucionRow.setEstado(estado);
				
				//Buscando roles por username
				Rol rol = new Rol();
				rol.setId(5);
				rol.setNombre("ROLE_INSTITUCION");	
				Set<Rol> rolesT = new HashSet();
				rolesT.add(rol);		
				List<Rol> roles = new ArrayList<Rol>(rolesT);
				institucionRow.setRoles(roles);
				
				return institucionRow;
			}
			return null;
		}

	}




	@Override
	public Boolean existenciaInstitucionPorUsername(String username) {
		logger.debug("------- Buscando existenciaInstitucionPorUsername ");

		String sql = "SELECT COUNT(*) from institucion i  "
				+ "WHERE i.inst_estado IN (1, 2) AND i.inst_email = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { username }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}



	@Override
	public JSONRespuesta listarInstitucionesAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "i.inst_id", "i.inst_nombre", "i.inst_email", "ti.tins_nombre", "e.est_nombre" };

		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM institucion i "
				+ "INNER JOIN tipo_institucion ti ON ti.tins_id = i.inst_tipo_institucion "
				+ "INNER JOIN estado e ON i.inst_estado = e.est_id "
				+ "WHERE i.inst_estado IN (1, 2) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (i.inst_nombre ILIKE ? OR i.inst_email ILIKE ? ";
			sql = sql + "OR ti.tins_nombre ILIKE ? OR e.est_nombre ILIKE ? ) ";

			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("1. Numeros de instituciones filtrados: " + sql);
		
			
		sql = "SELECT inst_id, inst_nombre, inst_email, tins_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "i.inst_id, i.inst_nombre, i.inst_email, ti.tins_nombre, e.est_nombre FROM institucion i "
				+ "INNER JOIN tipo_institucion ti ON ti.tins_id = i.inst_tipo_institucion "
				+ "INNER JOIN estado e ON i.inst_estado = e.est_id "
				+ "WHERE i.inst_estado IN (1, 2) "
				+ "AND (i.inst_nombre ILIKE ? OR i.inst_email ILIKE ? OR ti.tins_nombre ILIKE ? OR e.est_nombre ILIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2. Numeros de instituciones filtrados:: " + sql);

		List<Institucion> listaInstitucion = jdbcTemplate.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Institucion>() {

					public Institucion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Institucion institucion = obtenerInstitucion(rs.getInt("inst_id"));
						return institucion;				
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaInstitucion);

		return respuesta;
		
	}



	@Override
	public Boolean institucionActiva(String username) {
		logger.debug("------- Buscando institucionActiva  ");

		String sql = "SELECT COUNT(*) from institucion i WHERE i.inst_email = ? AND i.inst_estado = ?;";

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { username, Constants.ID_ESTADO_ACTIVO_ENTIDADES_PRIMARIAS }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}



	@Override
	public List<Institucion> listaIntituciones() {
		logger.debug("lista  -- Buscando usuario por username (email) pero sin password, pero con roles ");

		String SQL = "SELECT i.inst_id, i.inst_nombre, i.inst_nit, i.inst_latitud, i.inst_longitud, i.inst_direccion, i.inst_telefono, "
				+ "i.inst_url, i.inst_descripcion, i.inst_email, i.inst_tipo_institucion, i.inst_estado, i.inst_imagen "
				+ "FROM institucion i WHERE  i.inst_estado IN (1, 2)  ";

		List<Institucion> institucionResponse = jdbcTemplate.query(SQL,  new RowMapper<Institucion>() {
			public Institucion mapRow(ResultSet rs, int rowNum ) throws SQLException{
				Institucion institucion = obtenerInstitucion(rs.getInt("inst_id"));
				return institucion;
			}
		});

		System.out.println("listaIntituciones -- Saliendo listado instituciones");
		return institucionResponse;
	}


	
	
}
