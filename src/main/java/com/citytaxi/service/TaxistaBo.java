package com.citytaxi.service;

import java.util.List;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;
import com.citytaxi.exception.LocalBlockedException;
import com.citytaxi.exception.MapNotFoundException;

public interface TaxistaBo {

	public Taxista findById(Integer id);
	
	public List<Taxista> findAllByStatus(TaxiStatus taxiStatus);
	
	public Taxista save(Taxista taxista) throws MapNotFoundException, LocalBlockedException;
	
	public void update(Taxista taxista);

	public Taxista findNearestTaxista(Local origem);
	
	public void deleteAll();
}
