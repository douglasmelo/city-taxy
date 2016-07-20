package com.citytaxi.repo;

import com.citytaxi.controller.domain.Passageiro;

public interface PassageiroDao {

	public Passageiro findById(Integer id);
	
	public Passageiro create(Passageiro passageiro);
	
	public void update(Passageiro passageiro);

	public void deleteAll();
}
