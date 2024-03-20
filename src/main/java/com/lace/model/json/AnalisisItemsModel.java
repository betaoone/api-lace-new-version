package com.lace.model.json;

public class AnalisisItemsModel {
	
	private String prueba;
	private String resultados;
	private String unidades;
	private String referencia;
	private String comentario;
	
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getPrueba() {
		return prueba;
	}
	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
	public String getResultados() {
		return resultados;
	}
	public void setResultados(String resultados) {
		this.resultados = resultados;
	}
	public String getUnidades() {
		return unidades;
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public AnalisisItemsModel(String prueba, String resultados, String unidades, String referencia,
			String comentario) {
		
		this.prueba = prueba;
		this.resultados = resultados;
		this.unidades = unidades;
		this.referencia = referencia;
		this.comentario = comentario;
	}
	public AnalisisItemsModel() {
		
	}
	
	

}
