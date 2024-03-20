package com.lace.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.lace.model.PacientesModel;

@Entity
@Table(name = "pacientes2")
public class PacientesEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "nombre", length = 100)
	private String nombre;
	
	@Column(name = "direccion", length = 100)
	private String direccion;
	
	@Column(name = "ciudad", length = 50)
	private String ciudad;
	
	@Column(name = "estado", length = 45)
	private String estado;
	
	@Column(name = "codpostal", length = 10)
	private String codigoPostal;
	
	@Column(name = "nacimiento")
	@Temporal(TemporalType.DATE)
	private Calendar fechaNacimiento ;
	
	@Column(name = "sexo", columnDefinition = "CHAR")
	private String sexo;
	
	@Column(name = "telefono")
	private String jsonTelefono;
	
	@Column(name = "tsangre", length = 15)
	private String tiposangre;
	
	@Column(name = "email", length = 100)
	private String email;
	
	@Column(name = "rfc")
	private String jsonRFC;
	
	@OneToMany(mappedBy = "pacientesEntity", cascade = CascadeType.ALL)
	private List<AnalisisEntity> analisis = new ArrayList<>();

	
	
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getJsonTelefono() {
		return jsonTelefono;
	}

	public void setJsonTelefono(String jsonTelefono) {
		this.jsonTelefono = jsonTelefono;
	}

	public String getTipoSangre() {
		return tiposangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tiposangre = tipoSangre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJsonRFC() {
		return jsonRFC;
	}

	public void setJsonRFC(String jsonRFC) {
		this.jsonRFC = jsonRFC;
	}

	public List<AnalisisEntity> getAnalisis(){
		return analisis;
	}
	
	public void setAnalisis(List<AnalisisEntity> analisis) {
		this.analisis = analisis;
	}
	
	public PacientesEntity(int id, String nombre, String direccion, String ciudad, String estado, String codigoPostal,
			Calendar fechaNacimiento, String sexo, String jsonTelefono, String tipoSangre, String email,
			String jsonRFC) {
		
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.estado = estado;
		this.codigoPostal = codigoPostal;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.jsonTelefono = jsonTelefono;
		this.tiposangre = tipoSangre;
		this.email = email;
		this.jsonRFC = jsonRFC;
	}

	public PacientesEntity(PacientesModel p) {
		this.id = p.getId();
		this.nombre = p.getNombre();
		this.direccion = p.getDireccion();
		this.ciudad = p.getCiudad();
		this.estado = p.getEstado();
		this.codigoPostal = p.getCodigopostal();
		this.fechaNacimiento = p.getNacimiento();
		this.sexo = p.getGenero();
		this.tiposangre = p.getTiposangre();
		this.email = p.getEmail();
	}

	public PacientesEntity() {
		
	}
	
	
	
	
	
	
	
}
