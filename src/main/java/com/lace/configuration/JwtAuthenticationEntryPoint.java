package com.lace.configuration;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		//System.out.println(request.getUserPrincipal());
		if(authException != null)
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No tiene autorización");
		
	}

	
}
