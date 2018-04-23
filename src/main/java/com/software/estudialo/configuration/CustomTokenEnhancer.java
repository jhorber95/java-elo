/**
 * 
 */
package com.software.estudialo.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.entities.Institucion;
import com.software.estudialo.entities.Rol;
import com.software.estudialo.entities.Usuario;

/**
 * @author Luis Llanos
 *
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {
    
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	InstitucionDao institucionDao;
	
	@Override
    public OAuth2AccessToken enhance(
     OAuth2AccessToken accessToken, 
     OAuth2Authentication authentication) {
    	
        Map<String, Object> additionalInfo = new HashMap<>();
        
        /*
         * Para enviar en el atributo el usuario o institucion que se va a logear, se debe validar primero
         * si es un usuario o institucion para obtener el mismo.
         * 
         */
        
        Boolean existeUsuario = usuarioDao.buscarExisteUsuarioPorUsername(authentication.getName());
		Boolean existeInstitucion = institucionDao.buscarExisteInstitucionPorUsername(authentication.getName());
		
		if (existeUsuario) {
			 //Obteniendo el usuario por username
	        System.out.println("Nombre de usuario : " + authentication.getName());
	        Usuario usuario = usuarioDao.obtenerUsuarioPorUsernameCompletoSinPassword(authentication.getName());
	        additionalInfo.put("cliente", usuario);
		}else if (existeInstitucion) {
			 //Obteniendo la institucion por username
	        System.out.println("Nombre de institucion : " + authentication.getName());
	        Institucion institucion = institucionDao.obtenerInstitucionPorUsernameCompletoSinPassword(authentication.getName());		
	        additionalInfo.put("cliente", institucion);
		}
       
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
