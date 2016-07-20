package com.citytaxi.repo;

import java.util.List;

import com.citytaxi.controller.domain.Local;

public interface LocalDao {
	
	public Local findById(Integer id);
	
	public List<Local> findByTime(Integer time);
	
	public Local create(Local local);
	
	public void update(Local local);

	public void deleteAll();
}
