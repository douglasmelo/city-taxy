package com.citytaxi.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.LocalStatus;
import com.citytaxi.controller.domain.Mapa;
import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;
import com.citytaxi.exception.LocalBlockedException;
import com.citytaxi.exception.MapNotFoundException;
import com.citytaxi.repo.TaxistaDao;
import com.citytaxi.service.LocalBo;
import com.citytaxi.service.MapaBo;
import com.citytaxi.service.TaxistaBo;
import com.citytaxi.wrapper.MapaCidade;

@Component(value="taxistaBo")
public class TaxistaBoImpl implements TaxistaBo{
	
	@Autowired
	private TaxistaDao taxistaDao;
	
	@Autowired
	private MapaBo mapaBo;
	
	@Autowired
	private LocalBo localBo;

	@Override
	public Taxista findById(Integer id) {
		Taxista taxista = taxistaDao.findById(id);
		return taxista;
	}

	@Override
	public Taxista save(Taxista taxista) throws MapNotFoundException, LocalBlockedException {
		Local local = taxista.getLocal();
		Mapa mapa = mapaBo.findByTime(taxista.getTempo());
		if(mapa == null) {
			try {
				//criar um novo mapa no tempo
				Local[][] mapaCidade = MapaCidade.getMapaCidadeCsv();
				Mapa novoMapa = new Mapa(mapaCidade, local.getTempo() == null ? taxista.getTempo() : local.getTempo());
				mapa = mapaBo.create(novoMapa);
			} catch (IOException e) {
				throw new MapNotFoundException("Mapa n‹o encontrado", e);
			}
		}
		Local[][] locais = mapa.getLocais();
//		Local novoLocal = locais[local.getX()][local.getY()];
		Local novoLocal = locais[local.getY()][local.getX()];
		if(novoLocal.getStatus().equals(LocalStatus.bloqueada)) {
			throw new LocalBlockedException("Local bloqueado, escolha outro local");
		}
		taxista.setLocal(novoLocal);
		Taxista persistedTasista = taxistaDao.save(taxista);
		
		novoLocal.getTaxistas().add(persistedTasista);
//		locais[local.getX()][local.getY()] = novoLocal;
		locais[local.getY()][local.getX()] = novoLocal;
		localBo.update(novoLocal);
		mapa.setLocais(locais);
		mapaBo.update(mapa);
		return persistedTasista;
	}

	@Override
	public void update(Taxista taxista) {
		taxistaDao.update(taxista);
	}

	@Override
	public List<Taxista> findAllByStatus(TaxiStatus taxiStatus) {
		return taxistaDao.findAllByStatus(taxiStatus);
	}

	@Override
	public Taxista findNearestTaxista(Local localPassageiro) {
		Map<Double, Taxista> map = new HashMap<Double, Taxista>();
		List<Taxista> availableTaxis = this.findAllByStatus(TaxiStatus.vazio);
		Double shortestDistance = null;
		
		//TODO: tentar considerar as ruas obstruidas.
		if(availableTaxis != null && !availableTaxis.isEmpty()) {
			for (Taxista taxista : availableTaxis) {
				Local localTaxista = taxista.getLocal();
				Double euclideanDistance = calcEuclideanDistance(localTaxista, localPassageiro);
				if(shortestDistance == null) {
					shortestDistance = euclideanDistance;
				}else if(euclideanDistance < shortestDistance) {
					shortestDistance = euclideanDistance;
				}
				map.put(euclideanDistance, taxista);
			}
		}
		Taxista taxista = map.get(shortestDistance);
		
		return taxista;
	}
	
	private Double calcEuclideanDistance(Local origem, Local destino) {
		Integer x1 = origem.getX();
		Integer y1 = origem.getY();
		Integer x2 = destino.getX();
		Integer y2 = destino.getY();
		
		double euclideanDistance = Math.sqrt(Math.pow((x1-x2), 2) + Math.pow(y1-y2, 2));
		return euclideanDistance;
	}

	@Override
	public void deleteAll() {
		taxistaDao.deleteAll();
	}

}
