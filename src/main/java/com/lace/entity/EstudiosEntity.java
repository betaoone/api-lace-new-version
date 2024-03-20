package com.lace.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "estudios2")
public class EstudiosEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "json_estudio")
	private String json_estudio;

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

	public String getJson_estudio() {
		return json_estudio;
	}

	public void setJson_estudio(String json_estudio) {
		this.json_estudio = json_estudio;
	}


	public EstudiosEntity() {
	
	}
	
	public EstudiosEntity(String nombre, String json_estudio) {
		
		this.nombre = nombre;
		this.json_estudio = json_estudio;
		
	}
	
	
}
