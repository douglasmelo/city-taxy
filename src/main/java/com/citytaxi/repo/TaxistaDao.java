package com.citytaxi.repo;

import java.util.List;

import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;

public interface TaxistaDao {

	public Taxista findById(Integer id);
	
	public Taxista save(Taxista taxista);
	
	public void update(Taxista taxista);

	public List<Taxista> findAllByStatus(TaxiStatus taxiStatus);

	public void deleteAll();
	
}
