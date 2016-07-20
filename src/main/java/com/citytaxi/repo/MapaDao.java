package com.citytaxi.repo;

import java.util.List;

import com.citytaxi.controller.domain.Mapa;

public interface MapaDao {

	public Mapa findById(Integer id);
	
	public Mapa findByTime(Integer time);
	
	public List<Mapa> findAll();
	
	public Mapa create(Mapa mapa);
	
	public void update(Mapa mapa);

	public Mapa findLastMap();

	public void deleteAll();
}
