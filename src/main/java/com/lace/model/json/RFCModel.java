package com.lace.model.json;

public class RFCModel {
	
	private String fiscal;
	private String razon;
	private String rfc;
	public String getFiscal() {
		return fiscal;
	}
	public void setFiscal(String fiscal) {
		this.fiscal = fiscal;
	}
	public String getRazon() {
		return razon;
	}
	public void setRazon(String razon) {
		this.razon = razon;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public RFCModel() {
		
	}
	public RFCModel(RFCModel rfc) {
		
		this.fiscal = rfc.getFiscal();
		this.razon = rfc.getRazon();
		this.rfc = rfc.getRfc();
	}
	
	
	
	

}
