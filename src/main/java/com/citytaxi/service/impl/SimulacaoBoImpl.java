package com.citytaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.LocalStatus;
import com.citytaxi.controller.domain.Mapa;
import com.citytaxi.controller.domain.Passageiro;
import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;
import com.citytaxi.service.LocalBo;
import com.citytaxi.service.MapaBo;
import com.citytaxi.service.SimulacaoBo;
import com.citytaxi.service.TaxistaBo;

@Component(value="simulacaoBo")
public class SimulacaoBoImpl implements SimulacaoBo{
	
	@Autowired
	private MapaBo mapaBo;
	
	@Autowired
	private LocalBo localBo;
	
	@Autowired
	private TaxistaBo taxistaBo;

	@Override
	public Mapa avancar() {
		//buscar ultimo mapa incrementar o tempo e salvar novo mapa com o tempo
		Mapa map = mapaBo.findLastMap();
		map.setId(null);
		map.setTempo(map.getTempo()+1);
		map.setLocais(atualizarLocais(map.getLocais(), map.getTempo()));
		Mapa novoMapa = mapaBo.create(map);
		
		//Vai até o passageiro
		List<Taxista> taxistaNoCaminho = taxistaBo.findAllByStatus(TaxiStatus.noCaminho);
		List<Taxista> taxistaCheio = taxistaBo.findAllByStatus(TaxiStatus.cheio);
		
		for (Taxista taxista : taxistaNoCaminho) {
			novoMapa = buscarPassageiro(taxista, novoMapa);
		}
		
		//Está levando o passageiro ao destino
		for(Taxista taxista: taxistaCheio) {
			novoMapa = levarPassageiro(taxista, novoMapa);
		}
		
		return novoMapa;
	}
	
	private Mapa levarPassageiro(Taxista taxista, Mapa novoMapa) {
		Local[][] locais = novoMapa.getLocais();
		Integer yLimite = locais.length -1;
		Integer xLimite = locais[yLimite-1].length -1;
		Local localAtual = taxista.getLocal();
		Passageiro passageiro = taxista.getPassageiro();
		Local destino = passageiro.getDestino();
		Local novoLocalAtual = avancarAoDestino(localAtual,destino, yLimite, xLimite, locais, taxista.getLocaisVisitados());
		taxista.getLocaisVisitados().add(novoLocalAtual);
		
		localAtual = locais[localAtual.getY()][localAtual.getX()];
		localAtual.getTaxistas().remove(taxista);
		if(chegouAoDestino(novoLocalAtual, destino)) {
			taxista.setPassageiro(null); //passageiro entregue
			taxista.setStatus(TaxiStatus.vazio);
			taxista.getLocaisVisitados().clear();
		}
		
		//atualizar taxista
		taxista.setLocal(novoLocalAtual);
		taxistaBo.update(taxista);
		//atualizar locais
		novoLocalAtual.getTaxistas().add(taxista);
		localBo.update(novoLocalAtual);
		localBo.update(localAtual);

		//atualizar mapa
		locais[novoLocalAtual.getY()][novoLocalAtual.getX()] = novoLocalAtual;
		locais[localAtual.getY()][localAtual.getX()] = localAtual;
		novoMapa.setLocais(locais);
		mapaBo.update(novoMapa);
		return novoMapa;
	}

	private Mapa buscarPassageiro(Taxista taxista, Mapa novoMapa) {
		Local[][] locais = novoMapa.getLocais();
		Integer yLimite = locais.length -1;
		Integer xLimite = locais[yLimite-1].length -1;
		Local localAtual = taxista.getLocal();
		Passageiro passageiro = taxista.getPassageiro();
		Local origem = passageiro.getOrigem();
		Local novoLocalAtual = avancarAoDestino(localAtual,origem, yLimite, xLimite, locais, taxista.getLocaisVisitados());
		taxista.getLocaisVisitados().add(novoLocalAtual);
		
		localAtual = locais[localAtual.getY()][localAtual.getX()];
		localAtual.getTaxistas().remove(taxista);
		if(chegouAoDestino(novoLocalAtual, origem)) {
			taxista.setStatus(TaxiStatus.cheio);
			novoLocalAtual.getPassageiros().remove(passageiro);
			taxista.getLocaisVisitados().clear();
		}
		//atualizar taxista
		taxista.setLocal(novoLocalAtual);
		taxistaBo.update(taxista);
		//atualizar locais
		novoLocalAtual.getTaxistas().add(taxista);
		localBo.update(novoLocalAtual);
		localBo.update(localAtual);
		
		//atualizar mapa
		locais[novoLocalAtual.getY()][novoLocalAtual.getX()] = novoLocalAtual;
		locais[localAtual.getY()][localAtual.getX()] = localAtual;
		novoMapa.setLocais(locais);
		mapaBo.update(novoMapa);
		return novoMapa;
	}

	private Local avancarAoDestino(Local localAtual, Local origem, Integer yLimite, Integer xLimite, Local[][] locais, List<Local> locaisVisitados) {
		Integer x1 = localAtual.getX();
		Integer x2 = origem.getX();
		
		Integer y1 = localAtual.getY();
		Integer y2 = origem.getY();
		
		if(x1<x2) {
			localAtual = proximaPosicaoDisponivelParaDireita(localAtual,yLimite, xLimite, locais, locaisVisitados);
		}else if(x2<x1) {
			localAtual = proximaPosicaoDisponivelParaEsquerda(localAtual,yLimite, xLimite, locais, locaisVisitados);
		}else if(y1 < y2) {
			localAtual = proximaPosicaoDisponivelParaBaixo(localAtual, yLimite, xLimite, locais, locaisVisitados);
		}else if(y2 < y1) {
			localAtual = proximaPosicaoDisponivelParaCima(localAtual, yLimite, xLimite, locais, locaisVisitados);
		}
		
		return localAtual;
	}

	private Local proximaPosicaoDisponivelParaCima(Local localAtual,
			Integer yLimite, Integer xLimite, Local[][] locais, List<Local> locaisVisitados) {
		if(emCimaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarYParaCima(localAtual, locais); //y1=y1-1;
		}else if(direitaLivre(localAtual, xLimite, locais, locaisVisitados)) {
			localAtual = avancarXParaDireita(localAtual, locais); //x1 = x1+1;
		}else if(esquerdaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarXParaEsqueda(localAtual, locais); //x1 = x1-1;
		}else if(embaixoLivre(localAtual, yLimite, locais, locaisVisitados)) {
			localAtual = avancarYParaBaixo(localAtual, locais); //y1=y1+1;
		}
		return localAtual;
	}

	private Local proximaPosicaoDisponivelParaBaixo(Local localAtual,
			Integer yLimite, Integer xLimite, Local[][] locais, List<Local> locaisVisitados) {
		if(embaixoLivre(localAtual, yLimite, locais, locaisVisitados)) {
			localAtual = avancarYParaBaixo(localAtual, locais); //y1=y1+1;
		}else if(direitaLivre(localAtual, xLimite, locais, locaisVisitados)) {
			localAtual = avancarXParaDireita(localAtual, locais); //x1 = x1+1;
		}else if(esquerdaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarXParaEsqueda(localAtual, locais); //x1 = x1-1;
		}else if(emCimaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarYParaCima(localAtual, locais); //y1=y1-1;
		}
		return localAtual;
	}

	private Local proximaPosicaoDisponivelParaEsquerda(Local localAtual,
			Integer yLimite, Integer xLimite, Local[][] locais, List<Local> locaisVisitados) {
		if(esquerdaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarXParaEsqueda(localAtual, locais); //x1 = x1-1;
		}else if(embaixoLivre(localAtual, yLimite, locais, locaisVisitados)) { 
			localAtual = avancarYParaBaixo(localAtual, locais); //y1=y1+1;
		}else if(emCimaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarYParaCima(localAtual, locais); //y1=y1-1;
		}else if(direitaLivre(localAtual, xLimite, locais, locaisVisitados)) {
			localAtual = avancarXParaDireita(localAtual, locais); //x1 = x1+1;
		}
		return localAtual;
	}

	private Local proximaPosicaoDisponivelParaDireita(Local localAtual,
			Integer yLimite, Integer xLimite, Local[][] locais, List<Local> locaisVisitados) {
		if(direitaLivre(localAtual, xLimite, locais, locaisVisitados)) {
			localAtual = avancarXParaDireita(localAtual, locais); //x1 = x1+1;
		}else if(embaixoLivre(localAtual, yLimite, locais, locaisVisitados)) { 
			localAtual = avancarYParaBaixo(localAtual, locais); //y1=y1+1;
		}else if(emCimaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarYParaCima(localAtual, locais); //y1=y1-1;
		}else if(esquerdaLivre(localAtual, locais, locaisVisitados)) {
			localAtual = avancarXParaEsqueda(localAtual, locais); //x1 = x1-1;
		}
		return localAtual;
	}

	private boolean emCimaLivre(Local localAtual, Local[][] locais, List<Local> locaisVisitados) {
		if(localAtual.getY() > 0) {
			Local novoLocal = locais[localAtual.getY()-1][localAtual.getX()];
			if(novoLocal.getStatus().equals(LocalStatus.vazia) && !locaisVisitados.contains(novoLocal)) {
				return true;
			}
		}
		return false;
	}

	private boolean embaixoLivre(Local localAtual, Integer yLimite, Local[][] locais, List<Local> locaisVisitados) {
		if(localAtual.getY() < yLimite) {
			Local novoLocal = locais[localAtual.getY()+1][localAtual.getX()];
			if(novoLocal.getStatus().equals(LocalStatus.vazia) && !locaisVisitados.contains(novoLocal)) {
				return true;
			}
		}
		return false;
	}

	private boolean esquerdaLivre(Local localAtual, Local[][] locais, List<Local> locaisVisitados) {
		if(localAtual.getX() > 0) {
			Local novoLocal = locais[localAtual.getY()][localAtual.getX()-1];
			if(novoLocal.getStatus().equals(LocalStatus.vazia) && !locaisVisitados.contains(novoLocal)) {
				return true;
			}
		}
		return false;
	}

	private boolean direitaLivre(Local localAtual, Integer xLimite, Local[][] locais, List<Local> locaisVisitados) {
		if(localAtual.getX() < xLimite) {
			Local novoLocal = locais[localAtual.getY()][localAtual.getX()+1];
			if(novoLocal.getStatus().equals(LocalStatus.vazia) && !locaisVisitados.contains(novoLocal)) {
				return true;
			}
		}
		return false;
	}

	private Local avancarYParaCima(Local localAtual, Local[][] locais) {
		return locais[localAtual.getY()-1][localAtual.getX()];
	}

	private Local avancarYParaBaixo(Local localAtual, Local[][] locais) {
		return locais[localAtual.getY()+1][localAtual.getX()];
	}

	private Local avancarXParaEsqueda(Local localAtual, Local[][] locais) {
		return locais[localAtual.getY()][localAtual.getX()-1];
	}

	private Local avancarXParaDireita(Local localAtual, Local[][] locais) {
		return locais[localAtual.getY()][localAtual.getX()+1];
	}

	private boolean chegouAoDestino(Local localAtual, Local origem) {
		if(localAtual.getY().equals(origem.getY()) && localAtual.getX().equals(origem.getX())) {
			return true;
		}
		return false;
	}

	private Local[][] atualizarLocais(Local[][] locais, Integer tempo) {
		for(int i=0; i<locais.length;i++) {
			for(int j=0; j<locais[i].length; j++) {
				locais[i][j].setId(null);
				locais[i][j].setTempo(tempo);
			}
		}
		return locais;
	}

	@Override
	public Mapa estadoAtual() {
		Mapa mapa = mapaBo.findLastMap();
		return mapa;
	}

}
