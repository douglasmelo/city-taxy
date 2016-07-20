package com.citytaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.Mapa;
import com.citytaxi.repo.MapaDao;
import com.citytaxi.service.LocalBo;
import com.citytaxi.service.MapaBo;

@Component(value="mapaBo")
public class MapaBoImpl implements MapaBo{
	
	@Autowired
	private MapaDao mapaDao;
	
	@Autowired
	private LocalBo localBo;

	@Override
	public Mapa findById(Integer id) {
		return mapaDao.findById(id);
	}

	@Override
	public Mapa findByTime(Integer time) {
		return mapaDao.findByTime(time);
	}

	@Override
	public Mapa create(Mapa mapa) {
		Local[][] locais = mapa.getLocais();
		for(int i= 0; i< locais.length; i++) {
			for(int j=0; j< locais[i].length; j++) {
				Local local = locais[i][j];
				local.setTempo(mapa.getTempo());
				localBo.create(local);
			}
		}
		return mapaDao.create(mapa);
	}

	@Override
	public void update(Mapa mapa) {
		mapaDao.update(mapa);
	}

	@Override
	public Mapa findLastMap() {
		return mapaDao.findLastMap();
	}

	@Override
	public void deleteAll() {
		mapaDao.deleteAll(); 
	}

}
