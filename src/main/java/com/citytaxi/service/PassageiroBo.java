package com.citytaxi.service;

import com.citytaxi.controller.domain.Passageiro;
import com.citytaxi.exception.LocalBlockedException;
import com.citytaxi.exception.MapNotFoundException;

public interface PassageiroBo {
	
	public Passageiro findById(Integer id);
	
	public Passageiro create(Passageiro passageiro) throws MapNotFoundException, LocalBlockedException;
	
	public void update(Passageiro passageiro);
	
	public void deleteAll();
}
