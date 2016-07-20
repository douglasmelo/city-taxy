package com.citytaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.repo.LocalDao;
import com.citytaxi.service.LocalBo;

@Component(value="localBo")
public class LocalBoImpl implements LocalBo{
	
	@Autowired
	private LocalDao localDao;

	@Override
	public Local findById(Integer id) {
		return localDao.findById(id);
	}

	@Override
	public List<Local> findByTime(Integer time) {
		return localDao.findByTime(time);
	}

	@Override
	public Local create(Local local) {
		return localDao.create(local);
	}

	@Override
	public void update(Local local) {
		localDao.update(local);
	}

	@Override
	public void deleteAll() {
		localDao.deleteAll();
	}

}
