package com.lace.model;
import com.google.gson.Gson;
import com.lace.entity.EstudiosEntity;
import com.lace.model.json.EstudioModel;

public class EstudiosModel {

	private int id;
	private String nombre;
	private EstudioModel json_estudio [];
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
	public EstudioModel[] getJson_estudio() {
		return json_estudio;
	}
	public void setJson_estudio(EstudioModel[] json_estudio) {
		this.json_estudio = json_estudio;
	}
	public EstudiosModel() {
		
	}
	
	public EstudiosModel(EstudiosModel e) {
		this.id = e.getId();
		this.json_estudio = e.getJson_estudio();
		this.nombre = e.getNombre();
	}
	public EstudiosModel(String nombre, EstudioModel[] json_estudio) {
		
		this.nombre = nombre;
		this.json_estudio = json_estudio;
	}
	
	public EstudiosModel(EstudiosEntity e) {
		this.id = e.getId();
		this.nombre = e.getNombre();
		
		Gson gson = new Gson();
		
		EstudioModel[] em = (EstudioModel[]) gson.fromJson(e.getJson_estudio(), EstudioModel[].class);
		this.json_estudio = em;
	}
	
	
	
}
