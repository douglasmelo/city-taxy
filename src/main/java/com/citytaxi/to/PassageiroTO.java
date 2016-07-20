package com.citytaxi.to;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.citytaxi.controller.domain.Passageiro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class PassageiroTO {

	private Integer id;
	private LocalTO origem;
	private LocalTO destino;
	private Integer tempo;
	
	public PassageiroTO(Integer id, LocalTO origem, LocalTO destino,
			Integer tempo) {
		this.id = id;
		this.origem = origem;
		this.destino = destino;
		this.tempo = tempo;
	}

	public PassageiroTO(Integer id, Integer tempo) {
		this.id = id;
		this.tempo = tempo;
	}

	public static PassageiroTO fromEntity(Passageiro passageiro) {
		return new PassageiroTO(passageiro.getId(), LocalTO.fromEntity(passageiro.getOrigem()),
				LocalTO.fromEntity(passageiro.getDestino()), passageiro.getTempo());
	}
	
	public static List<PassageiroTO> fromEntities(List<Passageiro> passageiros) {
		List<PassageiroTO> list = new ArrayList<PassageiroTO>();
		if(passageiros != null && !passageiros.isEmpty()) {
			for (Passageiro passageiro : passageiros) {
				list.add(new PassageiroTO(passageiro.getId(), passageiro.getTempo()));
			}
		}
		return list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalTO getOrigem() {
		return origem;
	}

	public void setOrigem(LocalTO origem) {
		this.origem = origem;
	}

	public LocalTO getDestino() {
		return destino;
	}

	public void setDestino(LocalTO destino) {
		this.destino = destino;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

}
