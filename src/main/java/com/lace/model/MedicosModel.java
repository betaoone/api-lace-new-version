package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.lace.entity.MedicosEntity;
import com.lace.model.json.HospitalModel;
import com.lace.model.json.TelefonosModel;

public class MedicosModel {

	private int id;
	private String nombre;
	private String domicilio;
	private String area;
	private String ciudad;
	private String estado;
	
	@JsonProperty("telefonos")
	private TelefonosModel jsonTel [];
	
	@JsonProperty("infoHosp")
	private HospitalModel jsonInfoHosp;
	
	private String email;
	
	
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
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
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
	public TelefonosModel[] getJsonTel() {
		return jsonTel;
	}
	public void setJsonTel(TelefonosModel[] jsonTel) {
		this.jsonTel = jsonTel;
	}
	public HospitalModel getJsonInfoHosp() {
		return jsonInfoHosp;
	}
	public void setJsonInfoHosp(HospitalModel jsonInfoHosp) {
		this.jsonInfoHosp = jsonInfoHosp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public MedicosModel() {
	}
	
	public MedicosModel(MedicosEntity me) {
		
		this.id = me.getId();
		this.nombre = me.getNombre();
		this.domicilio = me.getDomicilio();
		this.area = me.getArea();
		this.ciudad = me.getCiudad();
		this.estado = me.getEstado();
		
		Gson gson = new Gson();
		HospitalModel hm = gson.fromJson(me.getJsonInfoHosp(), HospitalModel.class);
		this.jsonInfoHosp = hm;
		
		//TelefonosModel[] telefonos = gson.fromJson(p.getJsonTelefono(), TelefonosModel[].class) ;
		TelefonosModel[] telefonos = gson.fromJson(me.getJsonTel(), TelefonosModel[].class );
		this.jsonTel = telefonos;
		
		this.email = me.getEmail();
	}
	
	
	
	
}
