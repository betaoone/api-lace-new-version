package com.lace.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.lace.model.AnalisisModel;

@Entity
@Table(name = "analisis2")
public class AnalisisEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "analisis")
	private String analisis;

	@Column(name = "area")
	private String area;

	@Column(name = "comentario")
	private String comentario;

	@Column(name = "fecha")
	//@Temporal(TemporalType.DATE)
	//private Date fecha;
private String fecha;
	@Column(name = "json")
	private String json;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pacientes")
	private PacientesEntity pacientesEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medicos")
	private MedicosEntity medicosEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnalisis() {
		return analisis;
	}

	public MedicosEntity getMedicosEntity() {
		return medicosEntity;
	}

	public void setMedicosEntity(MedicosEntity medicosEntity) {
		this.medicosEntity = medicosEntity;
	}

	public void setAnalisis(String analisis) {
		this.analisis = analisis;
	}

	public String getArea() {
		return area;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void setArea(String area) {
		this.area = area;
	}

	//public Date getFecha() {
	public String getFecha() {	
	return fecha;
	}

	//public void setFecha(Date fecha) {
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public PacientesEntity getPacientesEntity() {
		return pacientesEntity;
	}

	public void setPacientesEntity(PacientesEntity pacientesEntity) {
		this.pacientesEntity = pacientesEntity;
	}

	public AnalisisEntity() {

	}

	public AnalisisEntity(int id, String analisis, String area, String comentario, String fecha, String json,//Date fecha, String json,
			PacientesEntity pacientesEntity, MedicosEntity medicosEntity) throws ParseException {

		this.id = id;
		this.analisis = analisis;
		this.area = area;
		this.comentario = comentario;
	
		this.fecha =  fecha;
		this.json = json;
		this.pacientesEntity = pacientesEntity;
		this.medicosEntity = medicosEntity;
	}

	public AnalisisEntity(AnalisisModel am) {
		this.analisis = am.getAnalisis();
		this.area = am.getArea();
		this.comentario = am.getComentario();

		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//try {
			//Date date = formatter.parse(am.getFecha());
			this.fecha = am.getFecha();//date;
		//} catch (ParseException e) {
		//	e.printStackTrace();
		//}
		
			
			// this.fecha = am.getFecha();
	}

}
