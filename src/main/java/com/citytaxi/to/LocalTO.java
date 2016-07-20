package com.citytaxi.to;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.LocalStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class LocalTO {

	private Integer id;
	private Integer x;
	private Integer y;
	private LocalStatus status;
	private Integer tempo;
	private List<TaxistaTO> taxistas;
	private List<PassageiroTO> passageiros;
	
	public LocalTO(Integer id, Integer x, int y, LocalStatus status,
			Integer tempo, List<TaxistaTO> taxistas, List<PassageiroTO> passageiros) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.status = status;
		this.tempo = tempo;
		this.taxistas = taxistas;
		this.passageiros = passageiros;
	}

	public static LocalTO fromEntity(Local local) {
		return new LocalTO(local.getId(), local.getX(), local.getY(), local.getStatus(), local.getTempo(), TaxistaTO.fromEntities(local.getTaxistas()), 
				PassageiroTO.fromEntities(local.getPassageiros()));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public LocalStatus getStatus() {
		return status;
	}

	public void setStatus(LocalStatus status) {
		this.status = status;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public List<TaxistaTO> getTaxistas() {
		return taxistas;
	}

	public void setTaxistas(List<TaxistaTO> taxistas) {
		this.taxistas = taxistas;
	}

	public List<PassageiroTO> getPassageiros() {
		return passageiros;
	}

	public void setPassageiros(List<PassageiroTO> passageiros) {
		this.passageiros = passageiros;
	}

}
