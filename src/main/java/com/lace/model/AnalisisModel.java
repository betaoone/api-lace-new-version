package com.lace.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.lace.entity.AnalisisEntity;
import com.lace.model.json.AAnalisisModel;

public class AnalisisModel {

	private int id;
	private String analisis;
	private String area;
	private String comentario;
	private String fecha;
	private String paciente;
	private String medico;
	private AAnalisisModel json[];
	
	

	public AAnalisisModel[] getJson() {
		return json;
	}

	public void setJson(AAnalisisModel[] json) {
		this.json = json;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnalisis() {
		return analisis;
	}

	public void setAnalisis(String analisis) {
		this.analisis = analisis;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public AnalisisModel(AnalisisEntity a) {

		this.id = a.getId();
		this.analisis = a.getAnalisis();
		this.area = a.getArea();
		this.comentario = a.getComentario();
		
		//System.out.println(a.getFecha().toString());
		
		//Date date = a.getFecha();
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//String dateStr = dateFormat.format(date);
		
		this.fecha = a.getFecha();// dateStr;//a.getFecha();
		//System.out.println(dateStr);
		
		PacientesModel pm = new PacientesModel(a.getPacientesEntity());
		MedicosModel mm = new MedicosModel(a.getMedicosEntity());
		
		this.paciente = pm.getNombre();
		this.medico = mm.getNombre();
		
		Gson gson = new Gson();
		
		AAnalisisModel[] am = (AAnalisisModel[]) gson.fromJson(a.getJson(), AAnalisisModel[].class);
		this.json = am;
		
		
	}

	public AnalisisModel(int id, String analisis, String area, String comentario, String fecha,
			AAnalisisModel[] json) {
	
		this.id = id;
		this.analisis = analisis;
		this.area = area;
		this.comentario = comentario;
		
	
		this.fecha = fecha;
		this.json = json;
	}

	public AnalisisModel() {

	}

}
