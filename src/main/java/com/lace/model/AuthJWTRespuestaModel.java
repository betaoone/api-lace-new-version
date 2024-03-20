package com.lace.model;

import java.io.Serializable;

public class AuthJWTRespuestaModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String token;
	public String getToken() {
		return token;
	}
	public AuthJWTRespuestaModel(String token) {
		
		this.token = token;
	}


	
	
}
