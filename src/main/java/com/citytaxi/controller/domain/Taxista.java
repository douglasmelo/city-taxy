package com.citytaxi.controller.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Taxista implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4610141521163719945L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn
	private Local local;
	
	@ManyToOne
	@JoinColumn
	private Passageiro passageiro;
	
	private TaxiStatus status;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn
	private List<Local> locaisVisitados;
	
	private String cor;
	
	private Integer tempo;
	
	public Taxista() {
		super();
	}

	public Taxista(Local local, TaxiStatus status, String cor, Integer tempo) {
		super();
		this.local = local;
		this.status = status;
		this.cor = cor;
		this.tempo = 0;
		this.tempo = tempo;
		this.locaisVisitados = new ArrayList<Local>();
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
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

	public Integer getId() {
		return id;
	}

	public Passageiro getPassageiro() {
		return passageiro;
	}

	public void setPassageiro(Passageiro passageiro) {
		this.passageiro = passageiro;
	}

	
	public List<Local> getLocaisVisitados() {
		return locaisVisitados;
	}

	public void setLocaisVisitados(List<Local> locaisVisitados) {
		this.locaisVisitados = locaisVisitados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Taxista other = (Taxista) obj;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status != other.status)
			return false;
		if (tempo == null) {
			if (other.tempo != null)
				return false;
		} else if (!tempo.equals(other.tempo))
			return false;
		return true;
	}
	
}
