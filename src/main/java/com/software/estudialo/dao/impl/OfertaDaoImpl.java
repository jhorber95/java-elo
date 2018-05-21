/**
 * 
 */
package com.software.estudialo.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.software.estudialo.dao.CategoriaDao;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.FreelancerDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.InteligenciaDao;
import com.software.estudialo.dao.JornadaDao;
import com.software.estudialo.dao.ModalidadDao;
import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.SubcategoriaDao;
import com.software.estudialo.dao.TipoOfertaDao;
import com.software.estudialo.dao.TipoOfreceDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.dao.impl.CategoriaDaoImpl.CategoriaRowMapper;
import com.software.estudialo.entities.Calificacion;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Evento;
import com.software.estudialo.entities.Freelancer;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.Inteligencia;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Jornada;
import com.software.estudialo.entities.Modalidad;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.ResultadoVocacional;
import com.software.estudialo.entities.Subcategoria;
import com.software.estudialo.entities.TipoOferta;
import com.software.estudialo.entities.TipoOfrece;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.util.Constants;

import io.swagger.models.auth.In;

// TODO: Auto-generated Javadoc
/**
 * The Class OfertaDaoImpl.
 *
 * @author LUIS
 */
@Repository("ofertaDao")
public class OfertaDaoImpl implements OfertaDao {

	/** The logger. */
	private Logger logger = Logger.getLogger(OfertaDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;

	/** The categoria dao. */
	@Autowired
	CategoriaDao categoriaDao;

	/** The tipo ofrece dao. */
	@Autowired
	TipoOfreceDao tipoOfreceDao;

	/** The tipo oferta dao. */
	@Autowired
	TipoOfertaDao tipoOfertaDao;

	/** The municipio dao. */
	@Autowired
	MunicipioDao municipioDao;

	/** The institucion dao. */
	@Autowired
	InstitucionDao institucionDao;

	/** The freelancer dao. */
	@Autowired
	FreelancerDao freelancerDao;

	@Autowired
	SubcategoriaDao subcategoriaDao;
	
	@Autowired
	InteligenciaDao inteligenciaDao;
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	JornadaDao jornadaDao;
	
	@Autowired
	ModalidadDao modalidadDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#obtenerOferta(int)
	 */
	@Override
	public Oferta obtenerOferta(int id) {
		logger.debug("obtenerOferta -- Buscando oferta por Id ");

		String SQL = "SELECT ofe_id, ofe_titulo, ofe_descripcion, ofe_precio, ofe_telefono, "
				+ "ofe_tipo_ofrece, ofe_categoria, ofe_municipio, ofe_tipo_oferta, ofe_fecha_hora, ofe_estado, ofe_imagen, ofe_destacada, ofe_jornada, ofe_modalidad "
				+ "FROM oferta WHERE ofe_id = ?;";

		Oferta ofertaResponse = jdbcTemplate.query(SQL, new Object[] { id }, new OfertaResultSetExtractor());
		logger.debug("obtenerOferta -- Saliendo oferta por Id");
		return ofertaResponse;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	

	@Override
	public List<Oferta> obtenerOfertasResultadTestVocacional(ResultadoVocacional resultadoVocacional) {

		logger.debug("obtenerOfertasResultadTestVocacional -- Buscando las ofertas segun el test vocacional ");

		int inteligencia1 = resultadoVocacional.getPrimero().getId();
		int inteligencia2 = resultadoVocacional.getSegundo().getId();
		int inteligencia3 = resultadoVocacional.getTercero().getId();

		String sql = "SELECT DISTINCT ofe_id, ofe_titulo, ofe_descripcion, ofe_precio, ofe_telefono, "
				+ "ofe_tipo_ofrece, ofe_categoria, ofe_municipio, ofe_tipo_oferta, ofe_fecha_hora, ofe_estado, ofe_imagen, ofe_destacada, ofe_jornada, ofe_modalidad "
				+ "FROM oferta ofe " 
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN subcategoria sub ON sub.sca_categoria = cat.cat_id "
				+ "INNER JOIN subcategoria_inteligencia sin ON sin.sin_subcategoria = sub.sca_id "
				+ "INNER JOIN inteligencia i ON i.int_id = sin.sin_inteligencia "
				+ "WHERE ofe.ofe_estado IN (8) AND i.int_id IN (?, ?, ?);";
		
		
		final List<Oferta> listaOfertas = new ArrayList<>();
		
		jdbcTemplate.query(sql,
				new Object[] { inteligencia1, inteligencia2, inteligencia3 }, 
				new RowCallbackHandler() {
					
				      @Override
				      public void processRow(ResultSet rs) throws SQLException {
				    	  
				    	  logger.debug("mapRow  ----- obteniendo ofertas");
							Oferta oferta = obtenerOferta(rs.getInt("ofe_id"));
							List<Inteligencia> inteligenciasTemporales = inteligenciaDao.obtenerInteligenciasOferta(oferta.getId());
							List<Inteligencia> inteligenciasFiltradas = new ArrayList<>();
							
							//filtrar inteligencias
							for (Inteligencia inteligencia : inteligenciasTemporales) {
								
								if (inteligencia.getId() == inteligencia1 || inteligencia.getId() == inteligencia2 || inteligencia.getId() == inteligencia3) {
									inteligenciasFiltradas.add(inteligencia);
								}												
							}
							
							oferta.setInteligencias(inteligenciasFiltradas);
							
							listaOfertas.add(oferta);											    	
				      }
				});
		
		
		// Ordenar ofertas por inteligencia
		List<Oferta> ordenadas = ordernarPorGanador(listaOfertas, resultadoVocacional);
		

		logger.debug(
				"obtenerOfertasResultadTestVocacional -- Saliendo de busqueda de las ofertas segun el test vocacional ");

		return ordenadas;
		
	}

	
	public List<Oferta> ordernarPorGanador(List<Oferta> listaOfertas, ResultadoVocacional resultadoVocacional){
		
		int inteligencia1 = resultadoVocacional.getPrimero().getId();
		int inteligencia2 = resultadoVocacional.getSegundo().getId();
		int inteligencia3 = resultadoVocacional.getTercero().getId();
		
		List<Oferta> ordenadas = new ArrayList<Oferta>();
		
		List<Oferta> grupo1 = new ArrayList<Oferta>();
		List<Oferta> grupo2 = new ArrayList<Oferta>();
		List<Oferta> grupo3 = new ArrayList<Oferta>();
		
		
		for (Oferta oferta : listaOfertas) {
			
			boolean repetido = false;
			
			for (Inteligencia inteligencia : oferta.getInteligencias()) {
				
				int inteligenciaId = inteligencia.getId();
				
				if ( inteligenciaId == inteligencia1) {
					
					if (repetido == false) {
						grupo1.add(oferta);
						repetido = true;
					}
					
				} else if (inteligenciaId  == inteligencia2){
					
					if (repetido == false) {
						grupo1.add(oferta);
						repetido = true;
					}
					
				} else if (inteligenciaId  == inteligencia3){
					
					if (repetido == false) {
						grupo1.add(oferta);
						repetido = true;
					}
					
				}
			}
						
		}
		
		
		if (!grupo1.isEmpty()) {
			ordenadas.addAll(grupo1);
		}
		
		if (!grupo2.isEmpty()) {
			ordenadas.addAll(grupo2);
		}
		
		if (!grupo3.isEmpty()) {
			ordenadas.addAll(grupo3);
		}
		
		return ordenadas;
}

		
	//------------------------------------------------------------------------------------------------------------------------------------------
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#listarOferta(java.lang.String,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarOfertaBuscador(String search, int start, int length, int draw, int posicion,
			String direccion) {

		logger.debug(" listarOferta ---- listar ofertas");

		JSONRespuesta respuesta = new JSONRespuesta();

		String[] campos = { "ofe.ofe_id", "ofe.ofe_titulo", "ofe.ofe_descripcion", "cat.cat_nombre", "tof.tof_nombre",
				"tio.tio_nombre", "mun.mun_nombre", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM oferta ofe " 
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " 
				+ "WHERE ofe.ofe_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de oferta =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql
					+ "AND ( ofe.ofe_titulo ILIKE ? OR ofe.ofe_descripcion ILIKE ? OR cat.cat_nombre ILIKE ? OR tof.tof_nombre ILIKE ? ";
			sql = sql + " OR tio.tio_nombre ILIKE ? OR mun.mun_nombre ILIKE ? OR est.est_nombre ILIKE ? ) ";

			filtrados = jdbcTemplate
					.queryForObject(sql,
							new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%",
									"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
							Integer.class);
		}

		System.out.println(" 1 ::: Consulta realizada para las ofertas: " + sql);

		/*sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, cat_nombre, tof_nombre, tio_nombre, mun_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " + "WHERE ofe.ofe_estado IN (8) "
				+ "AND ( unaccent(ofe.ofe_titulo) ILIKE ? OR unaccent(ofe.ofe_descripcion) ILIKE ?  "
				+ "OR unaccent(cat.cat_nombre) ILIKE ? OR  unaccent(tof.tof_nombre) ILIKE ? "
				+ "OR  unaccent(tio.tio_nombre) ILIKE ? "
				+ "OR  unaccent(mun.mun_nombre) ILIKE ? OR est.est_nombre ILIKE ? ))";
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";*/
		
		sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, cat_nombre, tof_nombre, tio_nombre, mun_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " 
				+ "WHERE ofe.ofe_estado IN (8) "
				+ "AND  ofe.ofe_titulo ILIKE ? "
				+ "OR  ofe.ofe_descripcion ILIKE ?  "
				+ "OR  cat.cat_nombre ILIKE ? "
				+ "OR  tof.tof_nombre ILIKE ? "
				+ "OR  tio.tio_nombre ILIKE ? "
				+ "OR  mun.mun_nombre ILIKE ? "
				+ "OR est.est_nombre ILIKE ? ) ";
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";

		System.out.println(" 2 ::: Consulta realizada para las ofertas: " + sql);
		
		final List<Oferta> listaOferta = new ArrayList<>();
		
		jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowCallbackHandler() {
			
				      @Override
				      public void processRow(ResultSet rs) throws SQLException {
				    	  
				    	  logger.debug("mapRow  ----- obteniendo ofertas");
							Oferta oferta = obtenerOferta(rs.getInt("ofe_id"));
							
							TipoOfrece tipoOfrece = oferta.getTipoOfrece();
							Boolean ofrecedorActivo = false;
							
							//Verificacion ofrecedor activo
							if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_INSTITUCION) {
								Institucion institucion = institucionDao.obtenerInstitucion(oferta.getIdOfrece());
								ofrecedorActivo = institucionDao.institucionActiva(institucion.getEmail());
								
							} else if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_FREELANCER) {							
								Usuario usuario = usuarioDao.obtenerUsuario(oferta.getIdOfrece());
								ofrecedorActivo = usuarioDao.usuarioActivo(usuario.getEmail());
							}
							
							if (ofrecedorActivo) {
								listaOferta.add(oferta);
							}				    	
				      }
				});
		
		

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaOferta);
		return respuesta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#listarOfertaFiltros(int, int,
	 * int, int, java.lang.String, int, int, int, int)
	 */
	@Override
	public JSONRespuesta listarOfertaFiltros(int start, int length, int draw, int posicion, String direccion,
			int categoria, int municipio, int tipoOfrece, int tipoOferta, int precioMinimo, int precioMaximo, String nombreOferta) {

		logger.debug(" listarOfertaFiltros ---- listar ofertas filtros");

		JSONRespuesta respuesta = new JSONRespuesta();

		String[] campos = { "ofe.ofe_id", "ofe.ofe_titulo", "ofe.ofe_descripcion", "cat.cat_nombre", "tof.tof_nombre",
				"tio.tio_nombre", "mun.mun_nombre", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM oferta ofe " + "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id WHERE ofe.ofe_estado IN (8) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de oferta =" + count);
		int filtrados = count;

		ArrayList<Object> parametros = new ArrayList<Object>();

		if (categoria != 0) {
			sql = sql + "AND ofe.ofe_categoria = ? ";
			parametros.add(categoria);
		}
		if (municipio != 0) {
			sql = sql + "AND ofe.ofe_municipio = ? ";
			parametros.add(municipio);
		}
		if (tipoOfrece != 0) {
			sql = sql + "AND ofe.ofe_tipo_ofrece = ? ";
			parametros.add(tipoOfrece);
		}
		if (tipoOferta != 0) {
			sql = sql + "AND ofe.ofe_tipo_oferta = ? ";
			parametros.add(tipoOferta);
		}
		if (precioMinimo != 0) {
			sql = sql + "AND ofe.ofe_precio >= ? ";
			parametros.add(precioMinimo);
		}
		if (nombreOferta != "") {
			sql = sql + "AND ofe.ofe_titulo ILIKE ? ";
			parametros.add("%" + nombreOferta + "%");
		}
		System.out.println(" =====================================");
		System.out.println("  Parametros busqueda filtro");
		System.out.println(" =====================================");
		System.out.println(parametros);
		sql = sql + "AND ofe.ofe_precio <= ? ";
		parametros.add(precioMaximo);

		 filtrados = jdbcTemplate.queryForObject(sql, parametros.toArray(), Integer.class);
		// filtrados = jdbcTemplate.queryForObject(sql, new Object[] {categoria }, Integer.class);

		System.out.println(" 1 ::: Consulta realizada para las ofertas: " + sql);

		String sql2 = "SELECT ofe_id, ofe_titulo, ofe_descripcion, cat_nombre, tof_nombre, tio_nombre, mun_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " + "WHERE ofe.ofe_estado IN (8) ";

		ArrayList<Object> parametros2 = new ArrayList<Object>();

		if (categoria != 0) {
			sql2 = sql2 + "AND ofe.ofe_categoria = ? ";
			parametros2.add(categoria);
		}
		if (municipio != 0) {
			sql2 = sql2 + "AND ofe.ofe_municipio = ? ";
			parametros2.add(municipio);
		}
		if (tipoOfrece != 0) {
			sql2 = sql2 + "AND ofe.ofe_tipo_ofrece = ? ";
			parametros2.add(tipoOfrece);
		}
		if (tipoOferta != 0) {
			sql2 = sql2 + "AND ofe.ofe_tipo_oferta = ? ";
			parametros2.add(tipoOferta);
		}
		if (precioMinimo != 0) {
			sql2 = sql2 + "AND ofe.ofe_precio >= ? ";
			parametros2.add(precioMinimo);
		}

		sql2 = sql2 + "AND ofe.ofe_precio <= ? ";
		parametros2.add(precioMaximo);

		parametros2.add(start);
		parametros2.add(fin);

		sql2 = sql2 + ") as tabla where tabla.RowNumber between ? and ? ";

		System.out.println(" 2 ::: Consulta realizada para las ofertas: " + sql2);

		final List<Oferta> listaOferta = new ArrayList<>();
		
		jdbcTemplate.query(sql2, parametros2.toArray(),
				new RowCallbackHandler() {
			
		      @Override
		      public void processRow(ResultSet rs) throws SQLException {
		    	  
		    	  logger.debug("mapRow  ----- obteniendo ofertas");
					Oferta oferta = obtenerOferta(rs.getInt("ofe_id"));
					
					TipoOfrece tipoOfrece = oferta.getTipoOfrece();
					Boolean ofrecedorActivo = false;
					
					//Verificacion ofrecedor activo
					if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_INSTITUCION) {
						Institucion institucion = institucionDao.obtenerInstitucion(oferta.getIdOfrece());
						ofrecedorActivo = institucionDao.institucionActiva(institucion.getEmail());
						
					} else if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_FREELANCER) {							
						Usuario usuario = usuarioDao.obtenerUsuario(oferta.getIdOfrece());
						ofrecedorActivo = usuarioDao.usuarioActivo(usuario.getEmail());
					}
					
					if (ofrecedorActivo) {
						listaOferta.add(oferta);
					}				    	
		      }
		}); 

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaOferta);
		return respuesta;

	}

	/**
	 * The Class OfertaRowMapper.
	 */
	class OfertaRowMapper implements RowMapper<Oferta> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
		 * int)
		 */
		@Override
		public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
			Oferta oferta = new Oferta();
			oferta.setId(rs.getInt("ofe_id"));
			oferta.setTitulo(rs.getString("ofe_titulo"));
			oferta.setDescripcion(rs.getString("ofe_descripcion"));
			oferta.setPrecio(rs.getString("ofe_precio"));
			oferta.setTelefono(rs.getString("ofe_telefono"));
			oferta.setImagenUrl(rs.getString("ofe_imagen"));
			oferta.setDestacada(rs.getBoolean("ofe_destacada"));

			Timestamp datetime = rs.getTimestamp("ofe_fecha_hora");
			System.out.println("datetime= " + datetime);
			oferta.setFechaHora(datetime);

			TipoOfrece tipoOfrece = tipoOfreceDao.obtenerTipoOfrece(rs.getInt("ofe_tipo_ofrece"));
			Categoria categoria = categoriaDao.obtenerCategoria(rs.getInt("ofe_categoria"));
			Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("ofe_municipio"));
			TipoOferta tipoOferta = tipoOfertaDao.obtenerTipoOferta(rs.getInt("ofe_tipo_oferta"));
			Estado estado = estadoDao.obtenerEstado(rs.getInt("ofe_estado"));
			Jornada jornada = jornadaDao.obtenerJornada(rs.getInt("ofe_jornada"));
			Modalidad modalidad = modalidadDao.obtenerModalidad(rs.getInt("ofe_modalidad"));
			

			oferta.setTipoOfrece(tipoOfrece);
			oferta.setCategoria(categoria);
			oferta.setMunicipio(municipio);
			oferta.setTipoOferta(tipoOferta);
			oferta.setEstado(estado);
			oferta.setJornada(jornada);
			oferta.setModalidad(modalidad);

			List<Subcategoria> subcategorias = subcategoriaDao.obtenerSubcategorias(rs.getInt("ofe_id"));
			oferta.setSubcategorias(subcategorias);

			Calificacion calificacion = new Calificacion();

			// Obtener el que oferta
			if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_INSTITUCION) {
				logger.debug("Ofrece la oferta: INSTITUCION -- Obteniendo oferta");
				Institucion institucion = institucionDao.obtenerInstitucionPorOferta(rs.getInt("ofe_id"));
				oferta.setIdOfrece(institucion.getId());
				oferta.setOfrece(institucion);

				Boolean existeCalificacionesInstitucion = existenCalificacionesOfertaInstitucion(oferta.getId());
				if (existeCalificacionesInstitucion) {
					calificacion = obtenerCalificacionOfertaInstitucion(oferta.getId());
				} else {
					calificacion.setPuntaje(0);
					calificacion.setPorcentaje(0);
				}

				oferta.setCalificacion(calificacion);

			} else if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_FREELANCER) {
				logger.debug("Ofrece la oferta: FREELANCER -- Obteniendo oferta");
				Freelancer freelancer = freelancerDao.obtenerFreelancerPorOferta(rs.getInt("ofe_id"));
				oferta.setIdOfrece(freelancer.getId());
				oferta.setOfrece(freelancer);

				Boolean existeCalificacionesFreelancer = existenCalificacionesOfertaFreelancer(oferta.getId());
				if (existeCalificacionesFreelancer) {
					calificacion = obtenerCalificacionOfertaFreelancer(oferta.getId());
				} else {
					calificacion.setPuntaje(0);
					calificacion.setPorcentaje(0);
				}

				oferta.setCalificacion(calificacion);
			}

			return oferta;
		}
	}

	/**
	 * The Class OfertaResultSetExtractor.
	 */
	class OfertaResultSetExtractor implements ResultSetExtractor<Oferta> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
		 * .ResultSet)
		 */
		@Override
		public Oferta extractData(ResultSet rs) throws SQLException, DataAccessException {

			if (rs.next()) {
				logger.debug("OfertaResultSetExtractor -- Obteniendo oferta");
				Oferta oferta = new Oferta();
				oferta.setId(rs.getInt("ofe_id"));
				oferta.setTitulo(rs.getString("ofe_titulo"));
				oferta.setDescripcion(rs.getString("ofe_descripcion"));
				oferta.setPrecio(rs.getString("ofe_precio"));
				oferta.setTelefono(rs.getString("ofe_telefono"));
				oferta.setImagenUrl(rs.getString("ofe_imagen"));
				oferta.setDestacada(rs.getBoolean("ofe_destacada"));

				Timestamp datetime = rs.getTimestamp("ofe_fecha_hora");
				System.out.println("datetime= " + datetime);
				oferta.setFechaHora(datetime);

				TipoOfrece tipoOfrece = tipoOfreceDao.obtenerTipoOfrece(rs.getInt("ofe_tipo_ofrece"));
				Categoria categoria = categoriaDao.obtenerCategoria(rs.getInt("ofe_categoria"));
				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("ofe_municipio"));
				TipoOferta tipoOferta = tipoOfertaDao.obtenerTipoOferta(rs.getInt("ofe_tipo_oferta"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("ofe_estado"));
				Jornada jornada = jornadaDao.obtenerJornada(rs.getInt("ofe_jornada"));
				Modalidad modalidad = modalidadDao.obtenerModalidad(rs.getInt("ofe_modalidad"));
				
				

				oferta.setTipoOfrece(tipoOfrece);
				oferta.setCategoria(categoria);
				oferta.setMunicipio(municipio);
				oferta.setTipoOferta(tipoOferta);
				oferta.setEstado(estado);
				oferta.setJornada(jornada);
				oferta.setModalidad(modalidad);

				List<Subcategoria> subcategorias = subcategoriaDao.obtenerSubcategorias(rs.getInt("ofe_id"));
				oferta.setSubcategorias(subcategorias);

				Calificacion calificacion = new Calificacion();

				// Obtener el que oferta
				if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_INSTITUCION) {
					logger.debug("Ofrece la oferta: INSTITUCION -- Obteniendo oferta");
					Institucion institucion = institucionDao.obtenerInstitucionPorOferta(rs.getInt("ofe_id"));
					oferta.setIdOfrece(institucion.getId());
					oferta.setOfrece(institucion);

					Boolean existeCalificacionesInstitucion = existenCalificacionesOfertaInstitucion(oferta.getId());
					if (existeCalificacionesInstitucion) {
						calificacion = obtenerCalificacionOfertaInstitucion(oferta.getId());
					} else {
						calificacion.setPuntaje(0);
						calificacion.setPorcentaje(0);
					}

					oferta.setCalificacion(calificacion);

				} else if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_FREELANCER) {
					logger.debug("Ofrece la oferta: FREELANCER -- Obteniendo oferta");
					Freelancer freelancer = freelancerDao.obtenerFreelancerPorOferta(rs.getInt("ofe_id"));
					oferta.setIdOfrece(freelancer.getId());
					oferta.setOfrece(freelancer);

					Boolean existeCalificacionesFreelancer = existenCalificacionesOfertaFreelancer(oferta.getId());
					if (existeCalificacionesFreelancer) {
						calificacion = obtenerCalificacionOfertaFreelancer(oferta.getId());
					} else {
						calificacion.setPuntaje(0);
						calificacion.setPorcentaje(0);
					}

					oferta.setCalificacion(calificacion);
				}

				return oferta;
			}
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.dao.OfertaDao#agregarOferta(com.software.estudialo
	 * .entities.Oferta)
	 */
	@Override
	public int agregarOferta(Oferta oferta) {

		logger.debug("--- AGREGANDO NUEVA OFERTA ------");

		try {

			String SQL1 = "INSERT INTO oferta (ofe_titulo, ofe_descripcion, ofe_precio, ofe_telefono, ofe_tipo_ofrece, ofe_categoria, ofe_municipio, ofe_tipo_oferta, "
					+ "ofe_fecha_hora, ofe_estado, ofe_destacada, ofe_imagen, ofe_jornada, ofe_modalidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			java.util.Date date = new java.util.Date(System.currentTimeMillis());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

			KeyHolder keyHolder1 = new GeneratedKeyHolder();

			int resultado1 = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, oferta.getTitulo());
					pstm.setString(2, oferta.getDescripcion());
					pstm.setInt(3, Integer.parseInt(oferta.getPrecio()));
					pstm.setString(4, oferta.getTelefono());
					pstm.setInt(5, oferta.getTipoOfrece().getId());
					pstm.setInt(6, oferta.getCategoria().getId());
					pstm.setInt(7, oferta.getMunicipio().getId());
					pstm.setInt(8, oferta.getTipoOferta().getId());
					pstm.setTimestamp(9, timestamp);
					pstm.setInt(10, Constants.ID_ESTADO_ACTIVO_ENTIDADES_SECUNDARIAS);
					pstm.setBoolean(11, false);
					pstm.setString(12, "default.png");
					pstm.setInt(13, oferta.getJornada().getId());
					pstm.setInt(14, oferta.getModalidad().getId());
					
					return pstm;
				}
			}, keyHolder1);

			if (resultado1 > 0) {
				logger.debug("Se inserto la oferta");

				int key = 0;

				if (keyHolder1.getKeys().size() == 1) {
					key = keyHolder1.getKey().intValue();
				} else if (keyHolder1.getKeys().size() > 1) {
					logger.debug("------- Se encuentra mas de una llave ----");
					key = Integer.parseInt(String.valueOf(keyHolder1.getKeys().get("ofe_id")));
				} else {
					logger.debug("------- No se pudo extraer la llave ----");
					//return false;
				}

				if (oferta.getTipoOfrece().getId() == Constants.ID_TIPO_OFRECE_FREELANCER) {

					int idFrelancer = freelancerDao.obtenerIdFreelancerConIdUsuario(oferta.getIdOfrece());

					String SQL2 = "INSERT INTO oferta_freelancer (ofr_oferta, ofr_freelancer) VALUES (?, ?);";

					int resultado2 = jdbcTemplate.update(SQL2, key, idFrelancer);

					if (resultado2 > 0) {
						logger.debug("Oferta Freelancer Insertado Correctamente!");

//						Boolean subcategoriasInsertadas = insertarSubcategoriasOferta(key, oferta.getSubcategorias());
//
//						if (subcategoriasInsertadas) {
//							logger.debug("Subcategorias insertadas correctamente ");
//							return true;
//						} else {
//							logger.debug("No se pudo insertar las subcategorias ");
//							return false;
//						}

					} else {
						logger.debug("No se pudo insertar Oferta Freelancer ");
						throw new DAOException("Ocurrio un inconveniente.");
					}

				} else if (oferta.getTipoOfrece().getId() == Constants.ID_TIPO_OFRECE_INSTITUCION) {

					Institucion institucion = institucionDao.obtenerInstitucion(oferta.getIdOfrece());

					String SQL3 = "INSERT INTO oferta_institucion (oins_oferta, oins_institucion) VALUES (?, ?);";

					int resultado3 = jdbcTemplate.update(SQL3, key, institucion.getId());

					if (resultado3 > 0) {
						logger.debug("Oferta Institucion Insertado Correctamente!");

//						Boolean subcategoriasInsertadas = insertarSubcategoriasOferta(key, oferta.getSubcategorias());
//
//						if (subcategoriasInsertadas) {
//							logger.debug("Subcategorias insertadas correctamente ");
//							return true;
//						} else {
//							logger.debug("No se pudo insertar las subcategorias ");
//							return false;
//						}

					} else {
						logger.debug("No se pudo insertar Oferta Institucion ");
						throw new DAOException("Ocurrio un inconveniente.");
					}

				}
				
				return key;

			} else {
				throw new DAOException("Ocurrio un inconveniente.");
			}

			

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Ocurrio un inconveniente.");
		}

	}

//	public Boolean insertarSubcategoriasOferta(int idOferta, List<Subcategoria> subcategorias) {
//
//		for (Subcategoria subcategoria : subcategorias) {
//
//			try {
//
//				String SQL1 = "INSERT INTO oferta_subcategoria (osu_oferta, osu_subcategoria) VALUES (?, ?);";
//
//				int resultado = jdbcTemplate.update(SQL1, idOferta, subcategoria.getId());
//
//				if (resultado > 0) {
//					logger.debug("Se inserto la subcategoria");
//					return true;
//				} else {
//					logger.debug("No se pudo insertar la subcategoria : " + subcategoria.getNombre());
//					return false;
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
//
//		}
//
//		return true;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#modificarOferta(int,
	 * com.software.estudialo.entities.Oferta)
	 */
	@Override
	public Boolean modificarOferta(int id, Oferta oferta) {
		logger.debug("--- DENTRO DE MODIFICAR oferta para el admin--------");
		
		String SQL1 = "UPDATE oferta SET ofe_estado = ?, ofe_destacada = ? WHERE ofe_id = ?;";
		
		String precioLimpio = oferta.getPrecio().replace(".", "");
		logger.debug("Precio: " + precioLimpio);
		
		oferta.setPrecio(precioLimpio);

		int resultado1 = jdbcTemplate.update(SQL1, oferta.getEstado().getId(), oferta.getDestacada(), oferta.getId());
		
//		int resultado1 = jdbcTemplate.update(SQL1, oferta.getTitulo(), oferta.getDescripcion(), Integer.parseInt(oferta.getPrecio()),
//				oferta.getTelefono(), oferta.getTipoOfrece().getId(), oferta.getCategoria().getId(),
//				oferta.getMunicipio().getId(), oferta.getTipoOferta().getId(), oferta.getEstado().getId(),
//				oferta.getDestacada(), oferta.getId());

		if (resultado1 > 0) {
			logger.debug("--- OFERTA MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el oferta");
			return false;
		}
		
		

//		String SQL1 = "UPDATE oferta SET ofe_titulo = ?, ofe_descripcion = ?, ofe_precio = ?, ofe_telefono = ?, "
//				+ "ofe_tipo_ofrece = ?, ofe_categoria = ?, ofe_municipio = ?, ofe_tipo_oferta = ?, "
//				+ "ofe_estado = ?, ofe_destacada = ? WHERE ofe_id = ?;";
//		
//		String precioLimpio = oferta.getPrecio().replace(".", "");
//		logger.debug("Precio: " + precioLimpio);
//		
//		oferta.setPrecio(precioLimpio);
//
//		int resultado1 = jdbcTemplate.update(SQL1, oferta.getTitulo(), oferta.getDescripcion(), Integer.parseInt(oferta.getPrecio()),
//				oferta.getTelefono(), oferta.getTipoOfrece().getId(), oferta.getCategoria().getId(),
//				oferta.getMunicipio().getId(), oferta.getTipoOferta().getId(), oferta.getEstado().getId(),
//				oferta.getDestacada(), oferta.getId());
//
//		if (resultado1 > 0) {
//			logger.debug("--- OFERTA MODIFICADa -----");
//			return true;
//		} else {
//			logger.debug("No se pudo MODIFICAR el oferta");
//			return false;
//		}
	}

	@Override
	public Boolean modificarOfertaGeneral(int id, Oferta oferta) {
		logger.debug("--- DENTRO DE MODIFICAR oferta para los usuarios--------");

		String SQL1 = "UPDATE oferta SET ofe_titulo = ?, ofe_descripcion = ?, ofe_precio = ?, ofe_telefono = ?, "
				+ "ofe_tipo_ofrece = ?, ofe_categoria = ?, ofe_municipio = ?, ofe_tipo_oferta = ?, ofe_jornada = ?, ofe_modalidad = ? WHERE ofe_id = ?;";

		
		String precioLimpio = oferta.getPrecio().replace(".", "");
		logger.debug("Precio: " + precioLimpio);
		
		oferta.setPrecio(precioLimpio);
		
		int resultado1 = jdbcTemplate.update(SQL1, oferta.getTitulo(), oferta.getDescripcion(), Integer.parseInt(oferta.getPrecio()),
				oferta.getTelefono(), oferta.getTipoOfrece().getId(), oferta.getCategoria().getId(),
				oferta.getMunicipio().getId(), oferta.getTipoOferta().getId(), oferta.getJornada().getId(), oferta.getModalidad().getId(), oferta.getId());

		if (resultado1 > 0) {
			logger.debug("--- OFERTA MODIFICADa -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el oferta");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#eliminarOferta(int)
	 */
	@Override
	public Boolean eliminarOferta(int id) {
		logger.debug("--- DENTRO DE ELIMINAR oferta --------");

		int idEliminado = 7;

		String SQL1 = "UPDATE oferta SET ofe_estado = ? WHERE ofe_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, idEliminado, id);

		if (resultado1 > 0) {
			logger.debug("--- oferta CAMBIADO DE ESTADO A ELIMINADO ------");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#buscarOferta(int)
	 */
	@Override
	public Boolean buscarOferta(int id) {
		logger.debug("------- BUSCANDO EXISTENCIA DE OFERTA ------");

		String sql = "SELECT COUNT(*) as cant FROM oferta o WHERE o.ofe_id = ? AND o.ofe_estado IN (8, 9)";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

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
	 * com.software.estudialo.dao.OfertaDao#buscarOferta(com.software.estudialo.
	 * entities.Oferta)
	 */
	@Override
	public Boolean buscarOferta(Oferta oferta) {
		logger.debug("------- Buscando oferta ");

		String sql = "SELECT COUNT(*) from oferta o " + "WHERE o.ofe_estado IN (8, 9) AND o.ofe_titulo = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { oferta.getTitulo() }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#listarOfertas(java.lang.String,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarOfertas(String search, int start, int length, int draw, int posicion, String direccion) {

		logger.debug(" listarOferta ---- listar ofertas");

		JSONRespuesta respuesta = new JSONRespuesta();

		String[] campos = { "ofe.ofe_id", "ofe.ofe_titulo", "ofe.ofe_descripcion", "cat.cat_nombre", "tof.tof_nombre",
				"tio.tio_nombre", "mun.mun_nombre", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM oferta ofe " + "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " + "WHERE ofe.ofe_estado IN (8, 9) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de oferta =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql
					+ "AND ( ofe.ofe_titulo LIKE ? OR ofe.ofe_descripcion LIKE ? OR cat.cat_nombre LIKE ? OR tof.tof_nombre LIKE ? ";
			sql = sql + " OR tio.tio_nombre LIKE ? OR mun.mun_nombre LIKE ? OR est.est_nombre LIKE ? ) ";

			filtrados = jdbcTemplate
					.queryForObject(sql,
							new Object[] { "&" + search + "%", "&" + search + "%", "%" + search + "%",
									"&" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
							Integer.class);
		}

		System.out.println(" 1 ::: Consulta realizada para las ofertas: " + sql);

		sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, cat_nombre, tof_nombre, tio_nombre, mun_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " + "WHERE ofe.ofe_estado IN (8, 9) "
				+ "AND (ofe.ofe_titulo LIKE ? OR ofe.ofe_descripcion LIKE ? OR cat.cat_nombre LIKE ? OR tof.tof_nombre LIKE ? OR tio.tio_nombre LIKE ? OR mun.mun_nombre LIKE ? OR est.est_nombre LIKE ? ))";
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";

		System.out.println(" 2 ::: Consulta realizada para las ofertas: " + sql);

		List<Oferta> listaOferta = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Oferta>() {

					public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("mapRow  ----- obteniendo ofertas");
						Oferta nodo = obtenerOferta(rs.getInt("ofe_id"));
						return nodo;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaOferta);
		return respuesta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.software.estudialo.dao.OfertaDao#obtenerIdOfertaPorIdOfertaFreelancer
	 * (int)
	 */
	@Override
	public int obtenerIdOfertaPorIdOfertaFreelancer(int idOfertaFreelancer) {

		logger.debug("------- Buscando al id oferta por el id de la oferta freelancer");

		String sql = "SELECT o.ofe_id " + "FROM oferta o "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_oferta = o.ofe_id " + "WHERE of.ofr_id = ? ";

		int idOferta = jdbcTemplate.queryForObject(sql, new Object[] { idOfertaFreelancer }, Integer.class);

		return idOferta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#
	 * obtenerIdOfertaPorIdOfertaInstitucion(int)
	 */
	@Override
	public int obtenerIdOfertaPorIdOfertaInstitucion(int idOfertaInstitucion) {

		logger.debug("------- Buscando al id oferta por el id de la oferta institucion");

		String sql = "SELECT o.ofe_id " + "FROM oferta o "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_oferta = o.ofe_id " + "WHERE oi.oins_id = ? ";

		int idOferta = jdbcTemplate.queryForObject(sql, new Object[] { idOfertaInstitucion }, Integer.class);

		return idOferta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#
	 * obtenerOfertasFreelancerDelEstudiante(int)
	 */
	@Override
	public List<Oferta> obtenerOfertasFreelancerDelEstudiante(int idEstudiante) {

		String SQL = "SELECT ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre "
				+ "FROM oferta ofe " + "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_oferta = ofe.ofe_id "
				+ "INNER JOIN inscripcion_oferta_freelancer iof ON iof.iof_oferta_freelancer = of.ofr_id "
				+ "INNER JOIN estudiante e ON e.estu_id = iof.iof_estudiante "
				+ "WHERE ofe.ofe_estado IN (8, 9) AND e.estu_id = ?";

		List<Oferta> listaOferta = jdbcTemplate.query(SQL, new Object[] { idEstudiante }, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				logger.debug("mapRow  ----- obteniendo ofertas");
				Oferta oferta = obtenerOferta(rs.getInt("ofe_id"));
				return oferta;
			}
		});

		return listaOferta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.software.estudialo.dao.OfertaDao#
	 * obtenerOfertasInstitucionDelEstudiante(int)
	 */
	@Override
	public List<Oferta> obtenerOfertasInstitucionDelEstudiante(int idEstudiante) {
		String SQL = "SELECT ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre "
				+ "FROM oferta ofe " + "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_oferta = ofe.ofe_id "
				+ "INNER JOIN inscripcion_oferta_institucion ioi ON ioi.ioi_oferta_institucion = oi.oins_id "
				+ "INNER JOIN estudiante e ON e.estu_id = ioi.ioi_estudiante "
				+ "WHERE ofe.ofe_estado IN (8, 9) AND e.estu_id = ?";

		List<Oferta> listaOferta = jdbcTemplate.query(SQL, new Object[] { idEstudiante }, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				logger.debug("mapRow  ----- obteniendo ofertas");
				Oferta nodo = obtenerOferta(rs.getInt("ofe_id"));
				return nodo;
			}
		});

		return listaOferta;
	}

	@Override
	public Boolean calificacionOfertaFreelancerRealizada(int idOferta, int idEstudiante) {

		logger.debug(" calificacionOfertaFreelancerRealizada --- Verificando si ya existe la calificacion ");

		String sql = "SELECT  COUNT(*) " + "FROM calificacion_oferta_freelancer cof "
				+ "INNER JOIN inscripcion_oferta_freelancer iof ON iof.iof_id = cof.cof_inscripcion_oferta "
				+ "INNER JOIN estudiante e ON e.estu_id = iof.iof_estudiante "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta "
				+ "WHERE o.ofe_estado IN (8, 9) AND o.ofe_id = ? AND e.estu_id = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta, idEstudiante }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Calificacion obtenerCalificacionOfertaFreelancer(int idOferta) {

		logger.debug(" obtenerCalificacionOfertaFreelancer --- obtener el promedio de calificaciones ");

		String sql = "SELECT avg(cof_calificacion_estudiante) "
				+ "FROM calificacion_oferta_freelancer cof, inscripcion_oferta_freelancer iof, oferta_freelancer ofr, oferta o "
				+ "WHERE cof.cof_inscripcion_oferta = iof.iof_id AND ofr.ofr_id = iof.iof_oferta_freelancer "
				+ "AND o.ofe_id = ofr.ofr_oferta AND o.ofe_estado IN (8, 9) AND o.ofe_id = ?";

		double promedio = jdbcTemplate.queryForObject(sql, new Object[] { idOferta }, Integer.class);
		double porcentaje = (promedio * 100) / 5;

		Calificacion calificacion = new Calificacion();
		calificacion.setPuntaje(promedio);
		calificacion.setPorcentaje(porcentaje);
		return calificacion;

	}

	@Override
	public Boolean calificarOfertaFreelancer(int idInscripcionOfertaFreelancer, double calificacion,
			String comentario) {

		logger.debug(" Calificando oferta frelancer ------");

		try {

			String SQL1 = "INSERT INTO calificacion_oferta_freelancer (cof_inscripcion_oferta, cof_calificacion_estudiante, cof_comentario_estudiante) "
					+ "VALUES (?, ?, ?);";

			int resultado1 = jdbcTemplate.update(SQL1, idInscripcionOfertaFreelancer, calificacion, comentario);

			if (resultado1 > 0) {
				logger.debug("Agregada la calificacion");
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Boolean calificacionOfertaInstitucionRealizada(int idOferta, int idEstudiante) {

		logger.debug(" calificacionOfertaInstitucionRealizada --- Verificando si ya existe la calificacion ");

		String sql = "SELECT  COUNT(*) " + "FROM calificacion_oferta_institucion coi "
				+ "INNER JOIN inscripcion_oferta_institucion ioi ON ioi.ioi_id = coi.coi_inscripcion_oferta "
				+ "INNER JOIN estudiante e ON e.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "WHERE o.ofe_estado IN (8, 9) AND o.ofe_id = ? AND e.estu_id = ?";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta, idEstudiante }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Calificacion obtenerCalificacionOfertaInstitucion(int idOferta) {

		logger.debug(" obtenerCalificacionOfertaInstitucion --- obtener el promedio de calificaciones ");

		String sql = "SELECT avg(coi_calificacion_estudiante) "
				+ "FROM calificacion_oferta_institucion coi, inscripcion_oferta_institucion ioi, oferta_institucion oi, oferta o  "
				+ "WHERE coi.coi_inscripcion_oferta = ioi.ioi_id AND oi.oins_id = ioi.ioi_oferta_institucion AND o.ofe_id = oi.oins_oferta "
				+ "AND o.ofe_estado IN (8, 9) AND o.ofe_id = ?";

		double promedio = jdbcTemplate.queryForObject(sql, new Object[] { idOferta }, Integer.class);
		double porcentaje = (promedio * 100) / 5;

		Calificacion calificacion = new Calificacion();
		calificacion.setPuntaje(promedio);
		calificacion.setPorcentaje(porcentaje);
		return calificacion;

	}

	@Override
	public Boolean calificarOfertaInstitucion(int idInscripcionOfertaInstitucion, double calificacion,
			String comentario) {

		logger.debug(" Calificando oferta institucion ------");

		try {

			String SQL1 = "INSERT INTO calificacion_oferta_institucion (coi_inscripcion_oferta, coi_calificacion_estudiante, coi_comentario_estudiante) "
					+ "VALUES (?, ?, ?);";

			int resultado1 = jdbcTemplate.update(SQL1, idInscripcionOfertaInstitucion, calificacion, comentario);

			if (resultado1 > 0) {
				logger.debug("Agregada la calificacion");
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Boolean existenCalificacionesOfertaFreelancer(int idOferta) {

		logger.debug(" existenCalificacionesOfertaFreelancer --- Verificando si existen calificaciones ");

		String sql = "SELECT  COUNT(*) " + "FROM calificacion_oferta_freelancer cof "
				+ "INNER JOIN inscripcion_oferta_freelancer iof ON iof.iof_id = cof.cof_inscripcion_oferta "
				+ "INNER JOIN estudiante e ON e.estu_id = iof.iof_estudiante "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_id = iof.iof_oferta_freelancer "
				+ "INNER JOIN oferta o ON o.ofe_id = of.ofr_oferta " + "WHERE o.ofe_estado IN (8, 9) AND o.ofe_id = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean existenCalificacionesOfertaInstitucion(int idOferta) {

		logger.debug(" existenCalificacionesOfertaInstitucion --- Verificando si existen calificaciones ");

		String sql = "SELECT  COUNT(*) " + "FROM calificacion_oferta_institucion coi "
				+ "INNER JOIN inscripcion_oferta_institucion ioi ON ioi.ioi_id = coi.coi_inscripcion_oferta "
				+ "INNER JOIN estudiante e ON e.estu_id = ioi.ioi_estudiante "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_id = ioi.ioi_oferta_institucion "
				+ "INNER JOIN oferta o ON o.ofe_id = oi.oins_oferta "
				+ "WHERE o.ofe_estado IN (8, 9) AND o.ofe_id = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean modificarImagenOferta(int idOferta, String newFileName) {

		logger.debug("--- DENTRO DE MODIFICAR IMAGEN OFERTA --------");

		String SQL1 = "UPDATE oferta SET ofe_imagen = ? WHERE ofe_id = ?;";

		int resultado1 = jdbcTemplate.update(SQL1, newFileName, idOferta);

		if (resultado1 > 0) {
			logger.debug("--- IMAGEN MODIFICADA ------");
			return true;

		} else {
			return false;
		}
	}

	@Override
	public List<Oferta> listarOfertasDestacadas() {
		logger.debug("listarOfertasDestacadas -- Buscando las ofertas destacadas ");

		String sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, ofe_precio, ofe_telefono, ofe_tipo_ofrece, ofe_categoria, ofe_municipio, "
				+ "ofe_tipo_oferta, ofe_fecha_hora, ofe_estado, ofe_imagen, ofe_destacada "
				+ "FROM oferta WHERE ofe_destacada = true;";

		final List<Oferta> listaOfertasDestacadas = new ArrayList<>();
		
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
		      @Override
		      public void processRow(ResultSet rs) throws SQLException {
		    	  
		    	  logger.debug("mapRow  ----- obteniendo ofertas");
					Oferta oferta = obtenerOferta(rs.getInt("ofe_id"));
					
					TipoOfrece tipoOfrece = oferta.getTipoOfrece();
					Boolean ofrecedorActivo = false;
					
					//Verificacion ofrecedor activo
					if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_INSTITUCION) {
						Institucion institucion = institucionDao.obtenerInstitucion(oferta.getIdOfrece());
						ofrecedorActivo = institucionDao.institucionActiva(institucion.getEmail());
						
					} else if (tipoOfrece.getId() == Constants.ID_TIPO_OFRECE_FREELANCER) {							
						Usuario usuario = usuarioDao.obtenerUsuario(oferta.getIdOfrece());
						ofrecedorActivo = usuarioDao.usuarioActivo(usuario.getEmail());
					}
					
					if (ofrecedorActivo) {
						listaOfertasDestacadas.add(oferta);
					}				    	
		      }
		});	
		
		
		logger.debug("listarOfertasDestacadas -- Saliendo de busqueda de las ofertas destacadas ");

		return listaOfertasDestacadas;
	}

	@Override
	public List<Oferta> getOfertasOfrecidasInstitucion(int idInstitucion) {
		logger.debug("getOfertasOfrecidasInstitucion -- Buscando las ofertas ofrecidas por una institucion ");

		String sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, ofe_precio, ofe_telefono, ofe_tipo_ofrece, ofe_categoria, ofe_municipio, "
				+ "ofe_tipo_oferta, ofe_fecha_hora, ofe_estado, ofe_imagen, ofe_destacada, ofe_jornada, ofe_modalidad  "
				+ "FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id "
				+ "INNER JOIN oferta_institucion oi ON oi.oins_oferta = ofe.ofe_id "
				+ "INNER JOIN institucion i ON i.inst_id = oi.oins_institucion "
				+ "WHERE ofe.ofe_estado IN (8, 9) AND i.inst_id = ?";

		List<Oferta> listaOfertasOfrecidas = jdbcTemplate.query(sql, new Object[] { idInstitucion }, new OfertaRowMapper());
		logger.debug("getOfertasOfrecidasInstitucion -- Saliendo de busqueda de las  ofrecidas por una institucion ");

		return listaOfertasOfrecidas;
	}

	@Override
	public List<Oferta> getOfertasOfrecidasFreelancer(int idFreelancer) {
		logger.debug("getOfertasOfrecidasFreelancer -- Buscando las ofertas ofrecidas por un freelancer ");

		String sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, ofe_precio, ofe_telefono, ofe_tipo_ofrece, ofe_categoria, ofe_municipio, "
				+ "ofe_tipo_oferta, ofe_fecha_hora, ofe_estado, ofe_imagen, ofe_destacada, ofe_jornada, ofe_modalidad  "
				+ "FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id "
				+ "INNER JOIN oferta_freelancer of ON of.ofr_oferta = ofe.ofe_id "
				+ "INNER JOIN freelancer f ON f.free_id = of.ofr_freelancer  "
				+ "WHERE ofe.ofe_estado IN (8, 9) AND f.free_id = ?";

		List<Oferta> listaOfertasOfrecidas = jdbcTemplate.query(sql, new Object[] { idFreelancer }, new OfertaRowMapper());
		logger.debug("getOfertasOfrecidasInstitucion -- Saliendo de busqueda de las  ofrecidas por un freelancer ");

		return listaOfertasOfrecidas;
	}

	@Override
	public Boolean estudianteEstadoInscritoOfertaInstitucion(int idInscripcionOfertaInstitucion) {
		
		logger.debug(" estudianteEstadoInscritoOfertaInstitucion --- Verificando INSCRITO estudiante en oferta de institucion ");

		String sql = "SELECT COUNT(*) "
				+ "FROM inscripcion_oferta_institucion ioi "
				+ "WHERE ioi.ioi_id = ? AND ioi.ioi_estado = ?";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idInscripcionOfertaInstitucion, Constants.ID_ESTADO_INSCRITO }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public Boolean estudianteEstadoInscritoOfertaFreelancer(int idInscripcionOfertaFreelancer) {
		logger.debug(" estudianteEstadoInscritoOfertaFreelancer --- Verificando INSCRITO estudiante en oferta de freelancer ");

		String sql = "SELECT COUNT(*) "
				+ "FROM inscripcion_oferta_freelancer iof "
				+ "WHERE iof.iof_id = ? AND iof.iof_estado = ?";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idInscripcionOfertaFreelancer, Constants.ID_ESTADO_INSCRITO }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JSONRespuesta listarOfertasAdmin(String search, int start, int length, int draw, int posicion,
			String direccion) {
		logger.debug(" listarOferta ---- listar ofertas");

		JSONRespuesta respuesta = new JSONRespuesta();

		String[] campos = { "ofe.ofe_id", "ofe.ofe_titulo", "ofe.ofe_descripcion", "cat.cat_nombre", "tof.tof_nombre",
				"tio.tio_nombre", "mun.mun_nombre", "est.est_nombre" };

		int fin = start + length;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM oferta ofe " + "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " + "WHERE ofe.ofe_estado IN (8, 9) ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("* Cantidad de oferta =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql
					+ "AND ( ofe.ofe_titulo ILIKE ? OR ofe.ofe_descripcion ILIKE ? OR cat.cat_nombre ILIKE ? OR tof.tof_nombre ILIKE ? ";
			sql = sql + " OR tio.tio_nombre ILIKE ? OR mun.mun_nombre ILIKE ? OR est.est_nombre ILIKE ? ) ";

			filtrados = jdbcTemplate
					.queryForObject(sql,
							new Object[] { "&" + search + "%", "&" + search + "%", "%" + search + "%",
									"&" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
							Integer.class);
		}

		System.out.println(" 1 ::: Consulta realizada para las ofertas: " + sql);

		sql = "SELECT ofe_id, ofe_titulo, ofe_descripcion, cat_nombre, tof_nombre, tio_nombre, mun_nombre, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "ofe.ofe_id, ofe.ofe_titulo, ofe.ofe_descripcion, cat.cat_nombre, tof.tof_nombre, tio.tio_nombre, mun.mun_nombre, est.est_nombre FROM oferta ofe "
				+ "INNER JOIN categoria cat ON ofe.ofe_categoria = cat.cat_id "
				+ "INNER JOIN tipo_ofrece tof ON ofe.ofe_tipo_ofrece = tof.tof_id "
				+ "INNER JOIN tipo_oferta tio ON ofe.ofe_tipo_oferta = tio.tio_id "
				+ "INNER JOIN municipio mun ON ofe.ofe_municipio = mun.mun_id "
				+ "INNER JOIN estado est ON ofe.ofe_estado = est.est_id " + "WHERE ofe.ofe_estado IN (8, 9) "
				+ "AND (ofe.ofe_titulo ILIKE ? OR ofe.ofe_descripcion ILIKE ? OR cat.cat_nombre ILIKE ? OR tof.tof_nombre ILIKE ? OR tio.tio_nombre ILIKE ? OR mun.mun_nombre ILIKE ? OR est.est_nombre ILIKE ? ))";
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";

		System.out.println(" 2 ::: Consulta realizada para las ofertas: " + sql);

		List<Oferta> listaOferta = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Oferta>() {

					public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("mapRow  ----- obteniendo ofertas");
						Oferta nodo = obtenerOferta(rs.getInt("ofe_id"));
						return nodo;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaOferta);
		return respuesta;
	}

	@Override
	public Boolean ofertaPerteneceFreelancer(int idOferta, int idFreelancer) {
		logger.debug(" ofertaPerteneceFreelancer --- Verificando si la oferta pertenece al freelancer ");

		String sql = "SELECT  COUNT(*) FROM oferta_freelancer of "
				+ "WHERE of.ofr_oferta = ? AND of.ofr_freelancer = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta, idFreelancer }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean ofertaPerteneceInstitucion(int idOferta, int idInstitucion) {
		logger.debug(" ofertaPerteneceInstitucion --- Verificando si la oferta pertenece a la institucion ");

		String sql = "SELECT  COUNT(*) FROM oferta_institucion oi "
				+ "WHERE oi.oins_oferta = ? AND oi.oins_institucion = ?;";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idOferta, idInstitucion }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	
}
