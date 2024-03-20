package com.lace.model.json;

public class HospitalModel {

	private String nombre;
	private String direccion;
	private String telefono;
	private String ciudad;
	private String estado;

	

	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public HospitalModel() {
		
	}
	public HospitalModel(HospitalModel hm) {
		
		this.nombre = hm.getNombre();
		this.direccion = hm.getDireccion();
		this.telefono = hm.getTelefono();
		this.ciudad = hm.getCiudad();
		this.estado = hm.getEstado();
	}
}
