package com.lace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lace.configuration.JwtTokenUtil;
import com.lace.model.AuthJWTPeticionModel;
import com.lace.model.AuthJWTRespuestaModel;
import com.lace.services.UsuariosService;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
public class AutenticacionController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuariosService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthJWTRespuestaModel> creaAutenticacion(@RequestBody AuthJWTPeticionModel jwtRequestModel) 
						throws Exception{
		autenticacion(jwtRequestModel.getUsername(), jwtRequestModel.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(jwtRequestModel.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		//UsuariosModel usuarioModel = usuariosService.obtenerUsuario(jwtRequestModel.getUsername());

		return ResponseEntity.ok(new AuthJWTRespuestaModel(token));
		
	}
	
	private void autenticacion(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
