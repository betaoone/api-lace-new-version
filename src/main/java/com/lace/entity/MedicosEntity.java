package com.lace.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lace.model.MedicosModel;

@Entity
@Table(name = "medicos2")
public class MedicosEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "domicilio")
	private String domicilio;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "ciudad")
	private String ciudad;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "telefono")
	private String jsonTel;
	
	@Column(name = "hospital")
	private String jsonInfoHosp;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(mappedBy = "medicosEntity", cascade = CascadeType.ALL)
	private List<AnalisisEntity> analisis = new ArrayList<>();

	
	
	public List<AnalisisEntity> getAnalisis() {
		return analisis;
	}

	public void setAnalisis(List<AnalisisEntity> analisis) {
		this.analisis = analisis;
	}

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

	public String getJsonTel() {
		return jsonTel;
	}

	public void setJsonTel(String jsonTel) {
		this.jsonTel = jsonTel;
	}

	public String getJsonInfoHosp() {
		return jsonInfoHosp;
	}

	public void setJsonInfoHosp(String jsonInfoHosp) {
		this.jsonInfoHosp = jsonInfoHosp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MedicosEntity() {
		
	}

	public MedicosEntity(MedicosModel m) {
		this.id = m.getId();
		this.nombre = m.getNombre();
		this.domicilio = m.getDomicilio();
		this.area = m.getArea();
		this.ciudad = m.getCiudad();
		this.estado = m.getEstado();
		//this.jsonTel = m.getJsonTel();
		//this.jsonInfoHosp = m.getJsonInfoHosp();
		this.email = m.getEmail();
	}
	
	
	
	
	
}
