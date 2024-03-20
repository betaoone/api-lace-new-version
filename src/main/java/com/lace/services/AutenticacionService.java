package com.lace.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lace.entity.UsuariosEntity;
import com.lace.repository.UsuariosRepository;

@Service("autenticacionService")
public class AutenticacionService implements UserDetailsService {

	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository uR;

	private Logger logger = LoggerFactory.getLogger(AutenticacionService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuariosEntity u = uR.findByNombreusuario(username);

		if(u == null) {
			logger.error("No existe el usuario: <"+ username + ">");
			throw new UsernameNotFoundException("Usuario <" + username + "> no existe");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>() ;
		
		authorities.add(new SimpleGrantedAuthority(u.getRol()));
	
		
		return new User(u.getNombreusuario(), u.getContrasena(), authorities);
	}

}
