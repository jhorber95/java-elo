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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.software.estudialo.dao.EstadoDao;
import com.software.estudialo.dao.EstudianteDao;
import com.software.estudialo.dao.FreelancerDao;
import com.software.estudialo.dao.GeneroDao;
import com.software.estudialo.dao.InscripcionOfertaFreelancerDao;
import com.software.estudialo.dao.MunicipioDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.TipoIdentificacionDao;
import com.software.estudialo.entities.Categoria;
import com.software.estudialo.entities.Estado;
import com.software.estudialo.entities.Estudiante;
import com.software.estudialo.entities.Genero;
import com.software.estudialo.entities.InscripcionOfertaFreelancer;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Municipio;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.TipoIdentificacion;

// TODO: Auto-generated Javadoc
/**
 * The Class EstudianteDaoImpl.
 *
 * @author LUIS
 */
@Repository("estudianteDao")
public class EstudianteDaoImpl implements EstudianteDao{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(EstudianteDaoImpl.class);

	/** The jdbc template object. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** The municipio dao. */
	@Autowired
	MunicipioDao municipioDao;
	
	/** The estado dao. */
	@Autowired
	EstadoDao estadoDao;
	
	/** The genero dao. */
	@Autowired
	GeneroDao generoDao;
	
	/** The oferta dao. */
	@Autowired
	OfertaDao ofertaDao;
	
	/** The tipo identificacion dao. */
	@Autowired
	TipoIdentificacionDao tipoIdentificacionDao;
	
	

	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstudianteDao#obtenerEstudiante(int)
	 */
	@Override
	public Estudiante obtenerEstudiante(int id) {
		logger.debug("obtenerEstudiante -- Buscando estudiante por Id ");

		String SQL = "SELECT u.usu_id, u.usu_email, u.usu_estado, p.per_tipo_identificacion, p.per_nombres, p.per_apellidos, p.per_municipio, "
				+ "p.per_telefono, p.per_identificacion, p.per_genero "
				+ "FROM estudiante est "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "INNER JOIN persona p ON p.per_id = u.usu_persona "
				+ "WHERE est.estu_id = ?;";

		Estudiante estudianteResponse = jdbcTemplate.query(SQL, new Object[] { id }, new EstudianteResultSetExtractor());
		
		logger.debug("obtenerEstudiante -- Saliendo estudiante por Id");
		return estudianteResponse;
	}
	
	
	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstudianteDao#buscarEstudiantePorIdUsuario(int)
	 */
	@Override
	public Boolean buscarEstudiantePorIdUsuario(int idUsuario) {
		logger.debug("------- Buscando si existe algun estudiante por el id usuario");
		
		String sql = "SELECT COUNT(*) as cant "
				+ "FROM estudiante est "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "WHERE u.usu_id = ?; ";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { idUsuario }, Integer.class);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstudianteDao#obtenerIdEstudiantePorIdUsuario(int)
	 */
	@Override
	public int obtenerIdEstudiantePorIdUsuario(int idUsuario) {
		logger.debug("------- Buscando al id estudiante por el id usuario");
		
		String sql = "SELECT est.estu_id "
				+ "FROM estudiante est "
				+ "INNER JOIN usuario u ON u.usu_id = est.estu_usuario "
				+ "WHERE u.usu_id = ?; ";

		int idEstudiante = jdbcTemplate.queryForObject(sql, new Object[]{idUsuario}, Integer.class);
						
		return idEstudiante;	
	}


	
	
	/**
	 * The Class EstudianteResultSetExtractor.
	 */
	class EstudianteResultSetExtractor implements ResultSetExtractor<Estudiante>{

		
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
		 */
		@Override
		public Estudiante extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			if (rs.next()) {				
				logger.debug("EstudianteResultSetExtractor -- Obteniendo estudiante");
				Estudiante estudianteRow = new Estudiante();
				// El id es el del usuario
				estudianteRow.setId(rs.getInt("usu_id"));
				estudianteRow.setNombres(rs.getString("per_nombres"));
				estudianteRow.setApellidos(rs.getString("per_apellidos"));
				estudianteRow.setTelefono(rs.getString("per_telefono"));
				estudianteRow.setIdentificacion(rs.getString("per_identificacion"));
				estudianteRow.setEmail(rs.getString("usu_email"));				
				
				Municipio municipio = municipioDao.obtenerMunicipio(rs.getInt("per_municipio"));
				Estado estado = estadoDao.obtenerEstado(rs.getInt("usu_estado"));
				Genero genero = generoDao.obtenerGenero(rs.getInt("per_genero"));
				TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao.obtenerTipoIdentificacion(rs.getInt("per_tipo_identificacion"));
				
				estudianteRow.setGenero(genero);
				estudianteRow.setMunicipio(municipio);
				estudianteRow.setTipoIdentificacion(tipoIdentificacion);
				estudianteRow.setEstado(estado);
				return estudianteRow;
			}
			return null;
		}
		
	}




	/* (non-Javadoc)
	 * @see com.software.estudialo.dao.EstudianteDao#listarInscripcionesEstudiante(int)
	 */
	@Override
	public List<Object> listarInscripcionesEstudiante(int idEstudiante) {
		
		//List<Object> inscripciones = new ArrayList<Object>();		
		
		//List<Oferta> ofertasFreelancer = ofertaDao.obtenerOfertasFreelancerDelEstudiante(idEstudiante);
		
//		List<InscripcionOfertaFreelancer> inscripcionOfertasDeFreelancer = inscripcionOfertaFreelancerDao.obtenerInscripcionesOfertasFreelancerDeEstudiante(idEstudiante);
//		
//		for (InscripcionOfertaFreelancer inscripcionOfertaFreelancer : inscripcionOfertasDeFreelancer) {
//			inscripciones.add(inscripcionOfertaFreelancer);
//		}
//		
//		return inscripciones;
		
		
		//List<Oferta> ofertasInstitucion = ofertaDao.obtenerOfertasInstitucionDelEstudiante(idEstudiante);	
		//List<Oferta> ofertas = new ArrayList<Oferta>();
//		ofertas.addAll(ofertasFreelancer);
//		ofertas.addAll(ofertasInstitucion);		
//		return ofertas;		
		return null;
	}
	
	
	
	

}
