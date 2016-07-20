package com.citytaxi.controller.domain;

public enum LocalStatus {
	
	vazia("_"),
	bloqueada("x");

	private final String status;
	
	LocalStatus(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
