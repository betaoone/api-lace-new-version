package com.lace.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstudioModel {

	private String subtitulo;
	
	@JsonProperty("items")
	private EstudioItemsModel items [];

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public EstudioItemsModel[] getItems() {
		return items;
	}

	public void setItems(EstudioItemsModel[] items) {
		this.items = items;
	}

	public EstudioModel() {
		
	}
	
	
	
	
	
}
