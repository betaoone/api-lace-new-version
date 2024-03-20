package com.lace.model.json;

public class EstudioItemsModel {
	
	private String prueba;
	private String unidades;
	private String referencia;
	public String getPrueba() {
		return prueba;
	}
	public void setPrueba(String prueba) {
		this.prueba = prueba;
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
	public EstudioItemsModel() {
		
	}
	public EstudioItemsModel(String prueba, String unidades, String referencia) {
		
		this.prueba = prueba;
		this.unidades = unidades;
		this.referencia = referencia;
	}
	
	
	
	
}
