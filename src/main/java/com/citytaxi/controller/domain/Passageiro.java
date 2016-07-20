package com.citytaxi.controller.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Passageiro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780902110328147619L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn
	private Local origem;
	
	@ManyToOne
	@JoinColumn
	private Local destino;
	
	private Integer tempo;
	
	public Passageiro() {
		super();
	}

	public Passageiro(Local origem, Local destino, Integer tempo) {
		super();
		this.origem = origem;
		this.destino = destino;
		this.tempo = tempo;
	}

	public Local getOrigem() {
		return origem;
	}

	public void setOrigem(Local origem) {
		this.origem = origem;
	}

	public Local getDestino() {
		return destino;
	}

	public void setDestino(Local destino) {
		this.destino = destino;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passageiro other = (Passageiro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tempo == null) {
			if (other.tempo != null)
				return false;
		} else if (!tempo.equals(other.tempo))
			return false;
		return true;
	}

}
