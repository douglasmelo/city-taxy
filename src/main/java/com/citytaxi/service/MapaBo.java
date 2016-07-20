package com.citytaxi.service;

import com.citytaxi.controller.domain.Mapa;

public interface MapaBo {

	public Mapa findById(Integer id);
	
	public Mapa findByTime(Integer time);
	
	public Mapa create(Mapa mapa);
	
	public void update(Mapa mapa);

	public Mapa findLastMap();
	
	public void deleteAll();
}
