package com.citytaxi.to;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class TaxistaTO {
	
	private Integer id;
	
	private LocalTO local;
	
	private TaxiStatus status;
	
	private String cor;
	
	private Integer tempo;
	
	public TaxistaTO(Integer id, String cor, TaxiStatus status,
			Integer tempo, LocalTO local) {
		this.id = id;
		this.cor = cor;
		this.status = status;
		this.tempo = tempo;
		this.local = local;
	}

	public TaxistaTO(Integer id, String cor, TaxiStatus status,
			Integer tempo) {
		this.id = id;
		this.cor = cor;
		this.status = status;
		this.tempo = tempo;
	}

	public static TaxistaTO fromEntity(Taxista taxista) {
		return new TaxistaTO(taxista.getId(), taxista.getCor(), taxista.getStatus(), 
				taxista.getTempo(), LocalTO.fromEntity(taxista.getLocal()));
	}
	
	public static List<TaxistaTO> fromEntities(List<Taxista> taxistas) {
		List<TaxistaTO> list = new ArrayList<TaxistaTO>();
		if(taxistas != null && !taxistas.isEmpty()) {
			for (Taxista taxista : taxistas) {
				list.add(new TaxistaTO(taxista.getId(), taxista.getCor(), taxista.getStatus(), taxista.getTempo()));
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

	public TaxiStatus getStatus() {
		return status;
	}

	public void setStatus(TaxiStatus status) {
		this.status = status;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public LocalTO getLocal() {
		return local;
	}

	public void setLocal(LocalTO local) {
		this.local = local;
	}
	
}
