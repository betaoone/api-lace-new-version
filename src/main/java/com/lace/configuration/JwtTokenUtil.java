package com.lace.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Valido por 1 hora
	public static final long JWT_TOKEN_VALIDITY = 3600000 * 8; //60000


	// Recibe username del token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// Recibe fecha de Expiración de token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	//Se obtienen los claims (información) del Token
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// Para recibir información del token se necesita la clave secret
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey("clave".getBytes()).parseClaimsJws(token).getBody();
	}

	// Verifica si el token expiró
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// Genera token para Usuario
	public String generateToken(UserDetails userDetails) throws IOException  {
		Map<String, Object> claims = new HashMap<>();
		
		//Agregamos los roles de usuario
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		return doGenerateToken(claims, userDetails.getUsername());
	}

	//genera el token con clave
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, "clave".getBytes()).compact();
	}

	// Valida token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
