package com.lace.model;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.lace.entity.PacientesEntity;
import com.lace.model.json.RFCModel;
import com.lace.model.json.TelefonosModel;

public class PacientesModel {

	private int id;
	private String ciudad;
	private String codigopostal;
	private String direccion;
	private String email;
	private String estado;
	private Calendar nacimiento;
	
    @JsonProperty("rfcjson")
	private RFCModel rfcjson;
    
    @JsonProperty("telefonos")
	private TelefonosModel telefonos [];
    
	private String nombre;
	private String genero;
	private String tiposangre;
	
		
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getCodigopostal() {
		return codigopostal;
	}


	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Calendar getNacimiento() {
		return nacimiento;
	}


	public void setNacimiento(Calendar nacimiento) {
		this.nacimiento = nacimiento;
	}


	public RFCModel getRfcjson() {
		return rfcjson;
	}


	public void setRfcjson(RFCModel rfcjson) {
		this.rfcjson = rfcjson;
	}


	public TelefonosModel[] getTelefonos() {
		return telefonos;
	}


	public void setTelefonos(TelefonosModel[] telefonos) {
		this.telefonos = telefonos;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public String getTiposangre() {
		return tiposangre;
	}


	public void setTiposangre(String tiposangre) {
		this.tiposangre = tiposangre;
	}


	public PacientesModel() {
		
	}
	
	
	public PacientesModel(int id, String ciudad, String codigopostal, String direccion, String email, String estado,
			Calendar nacimiento, RFCModel rfcjson, TelefonosModel[] telefonos, String nombre, String genero,
			String tiposangre) {
		
		this.id = id;
		this.ciudad = ciudad;
		this.codigopostal = codigopostal;
		this.direccion = direccion;
		this.email = email;
		this.estado = estado;
		this.nacimiento = nacimiento;
		this.rfcjson = rfcjson;
		this.telefonos = telefonos;
		this.nombre = nombre;
		this.genero = genero;
		this.tiposangre = tiposangre;
	}
	public PacientesModel(PacientesEntity p) {
	
		this.id = p.getId();
		this.ciudad = p.getCiudad();
		this.codigopostal = p.getCodigoPostal();
		this.direccion = p.getDireccion();
		this.email = p.getEmail();
		this.estado = p.getEstado();
		this.nacimiento = p.getFechaNacimiento();
		
		Gson gson = new Gson();
		RFCModel model = gson.fromJson(p.getJsonRFC(), RFCModel.class);
		this.rfcjson = model;
		
		
		TelefonosModel[] telefonos = gson.fromJson(p.getJsonTelefono(), TelefonosModel[].class) ;
		this.telefonos = telefonos;
		
		this.nombre = p.getNombre();
		this.genero = p.getSexo();
		this.tiposangre = p.getTipoSangre();
	}
	
	
}
