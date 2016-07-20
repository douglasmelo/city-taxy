package com.citytaxi.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.LocalStatus;
import com.citytaxi.controller.domain.Mapa;
import com.citytaxi.controller.domain.Passageiro;
import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;
import com.citytaxi.exception.LocalBlockedException;
import com.citytaxi.exception.MapNotFoundException;
import com.citytaxi.repo.PassageiroDao;
import com.citytaxi.service.LocalBo;
import com.citytaxi.service.MapaBo;
import com.citytaxi.service.PassageiroBo;
import com.citytaxi.service.TaxistaBo;
import com.citytaxi.wrapper.MapaCidade;

@Component(value="passageiroBo")
public class PassageiroBoImpl implements PassageiroBo{
	
	@Autowired
	private PassageiroDao passageiroDao;
	
	@Autowired
	private MapaBo mapaBo;
	
	@Autowired
	private LocalBo localBo;
	
	@Autowired
	private TaxistaBo taxistaBo;

	@Override
	public Passageiro findById(Integer id) {
		return passageiroDao.findById(id);
	}

	@Override
	public Passageiro create(Passageiro passageiro) throws MapNotFoundException, LocalBlockedException {
		Local origem = passageiro.getOrigem();
		Local destino = passageiro.getDestino();
		
		Mapa mapa = mapaBo.findByTime(passageiro.getTempo());
		if(mapa == null) {
			try {
				Local[][] mapaCidade = MapaCidade.getMapaCidadeCsv();
				Mapa novoMapa = new Mapa(mapaCidade, passageiro.getTempo());
				mapa = mapaBo.create(novoMapa);
			}catch(IOException e) {
				throw new MapNotFoundException("Mapa n‹o encontrado", e);
			}
		}
		Local[][] locais = mapa.getLocais();
		Local novoLocalOrigem = locais[origem.getY()][origem.getX()];
		if(novoLocalOrigem.getStatus().equals(LocalStatus.bloqueada)) {
			throw new LocalBlockedException("Local de origem bloqueado, escolha outro local");
		}
		Local novoLocalDestino = locais[destino.getY()][destino.getX()];
		if(novoLocalDestino.getStatus().equals(LocalStatus.bloqueada)) {
			throw new LocalBlockedException("Local de destino bloqueado, escolha outro local");
		}
		passageiro.setOrigem(novoLocalOrigem);
		passageiro.setDestino(novoLocalDestino);
		Passageiro persistedPassageiro = passageiroDao.create(passageiro);
		
		novoLocalOrigem.getPassageiros().add(persistedPassageiro);
		localBo.update(novoLocalOrigem);
		locais[origem.getY()][origem.getX()] = novoLocalOrigem;
		
		Taxista nearestTaxista = taxistaBo.findNearestTaxista(origem);
		Local localTaxista = null;
		Local novoLocalTaxista = null;
		if(nearestTaxista != null) {
			localTaxista = nearestTaxista.getLocal();
			novoLocalTaxista = locais[localTaxista.getY()][localTaxista.getX()];
			novoLocalTaxista.getTaxistas().remove(nearestTaxista);
			nearestTaxista.setPassageiro(persistedPassageiro);
			nearestTaxista.setStatus(TaxiStatus.noCaminho);
			taxistaBo.update(nearestTaxista);
			novoLocalTaxista.getTaxistas().add(nearestTaxista);
		}
		if(localTaxista != null) {
			locais[localTaxista.getY()][localTaxista.getX()] = novoLocalTaxista;
		}
		mapa.setLocais(locais);
		mapaBo.update(mapa);
		
		return persistedPassageiro;
	}

	@Override
	public void update(Passageiro passageiro) {
		passageiroDao.update(passageiro);
	}

	@Override
	public void deleteAll() {
		passageiroDao.deleteAll();
	}

}
