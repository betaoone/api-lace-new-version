package com.lace.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lace.model.UsuariosModel;

@Entity
@Table(name = "usuarios2")
public class UsuariosEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "nombreusuario", length = 45)
	private String nombreusuario;

	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "rol")
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

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
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

	public UsuariosEntity() {

	}

	public UsuariosEntity(int id, String nombre, String nombreUsuario, 
			String contrasena, String email, String rol) {

		this.id = id;
		this.nombre = nombre;
		this.nombreusuario = nombreUsuario;
		this.contrasena = contrasena;
		this.email = email;
		this.rol = rol;
	}

	public UsuariosEntity(UsuariosModel u) {

		this.id = u.getId();
		this.nombre = u.getNombre();
		this.nombreusuario = u.getNombreUsuario();
		this.contrasena = u.getContrasena();
		this.email = u.getEmail();
		this.rol = u.getRol();
	}

	public UsuariosEntity(UsuariosModel u, int id) {

		this.id = id;
		this.nombre = u.getNombre();
		this.nombreusuario = u.getNombreUsuario();
		//this.contrasena = u.getContrasena();
		this.email = u.getEmail();
		this.rol = u.getRol();
	}

	/*
	 * public UsuariosEntity(int id, String nombre, String nombreusuario, String
	 * contrasena, String email) {
	 * 
	 * this.id = id; this.nombre = nombre; this.nombreUsuario = nombreusuario;
	 * this.contrasena = contrasena; this.email = email; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}
