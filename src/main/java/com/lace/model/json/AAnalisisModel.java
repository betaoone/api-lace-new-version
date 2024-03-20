package com.lace.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AAnalisisModel {

	private String subtitulo;
	
	@JsonProperty("items")
	private AnalisisItemsModel items [];
	
	public String getSubtitulo() {
		return subtitulo;
	}
	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}
	public AnalisisItemsModel[] getItems() {
		return items;
	}
	public void setItems(AnalisisItemsModel[] items) {
		this.items = items;
	}
	public AAnalisisModel(AnalisisItemsModel[] items) {
		
		this.items = items;
	}
	public AAnalisisModel() {
		
	}
	
	
	
}
