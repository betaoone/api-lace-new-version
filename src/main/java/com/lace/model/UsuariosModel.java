package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.lace.entity.UsuariosEntity;

public class UsuariosModel {

	private int id;
	private String nombre;
	//private String direccion;
	//private String telefono;
	private String nombreUsuario;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String contrasena;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String repetirContrasena;
	private String email;
	
	private String rol;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getRepetirContrasena() {
		return repetirContrasena;
	}

	public void setRepetirContrasena(String repetirContrasena) {
		this.repetirContrasena = repetirContrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public UsuariosModel() {
		
	}
	
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
	public UsuariosModel(UsuariosEntity u) {
		
		this.id = u.getId();
		this.nombre = u.getNombre();
		this.nombreUsuario = u.getNombreusuario();
		this.email = u.getEmail();
		this.rol = u.getRol();
		this.contrasena = u.getContrasena();
	}
	
	
	
	
}
