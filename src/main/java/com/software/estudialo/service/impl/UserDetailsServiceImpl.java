/**
 * 
 */
package com.software.estudialo.service.impl;

/**
 * @author LUIS
 *
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.RolDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.dao.impl.UsuarioDaoImpl;
import com.software.estudialo.entities.CustomUserDetail;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.entities.Usuario;
import com.software.estudialo.exception.CustomOauthException;
import com.software.estudialo.exception.CustomOauthExceptionSerializer;

/**
 * @author LUIS
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
		
	private Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	InstitucionDao institucionDao;
	
	@Autowired
	RolDao rolDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		/**
		 * Validamos si es un usuario o institucion
		 */
		Boolean existeUsuario = usuarioDao.buscarExisteUsuarioPorUsername(username);
		Boolean existeInstitucion = institucionDao.buscarExisteInstitucionPorUsername(username);
		Set<Rol> roles = null;
		Usuario usuario = null;
		Institucion institucion = null;
		
		if (existeUsuario) {
			usuario = usuarioDao.buscarUsuarioPorUsername(username);
			roles = rolDao.buscarRolesPorUsername(username);
		}else if (existeInstitucion) {
			institucion = institucionDao.buscarInstitucionPorUsername(username);
			logger.debug("Password Institucion: " + institucion.getPassword());
			Rol rol = new Rol();
			rol.setId(5);
			rol.setNombre("ROLE_INSTITUCION");			
			roles = new HashSet();
			roles.add(rol);			
		}
		
		if (existeUsuario == false && existeInstitucion == false) {
			throw new CustomOauthException("usuario_no_existe");			
        }	
		
		Boolean clienteActivo = false;
		
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getNombre()));
            logger.debug("Role: " + role.getNombre());
        });		
		
		logger.debug("********* Construyendo usuario o institucion .... ");
		
		UserDetails userDetails = null;
		
		if (existeUsuario) {
			userDetails = new org.springframework.security.core.userdetails.
	                User(usuario.getEmail(), usuario.getPassword(), authorities);
			
			clienteActivo = usuarioDao.usuarioActivo(username);
			
		}else if (existeInstitucion) {
			userDetails = new org.springframework.security.core.userdetails.
	                User(institucion.getEmail(), institucion.getPassword(), authorities);
			
			clienteActivo = institucionDao.institucionActiva(username);
		}
		
		if (!clienteActivo) {
			throw new CustomOauthException("usuario_no_activo");
		}
			
		return userDetails;
	}
	
}