package com.citytaxi.controller.domain;

public enum TaxiStatus {

	vazio(1),
	noCaminho(2),
	cheio(3);
	
	private final Integer status;
	
	TaxiStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}
