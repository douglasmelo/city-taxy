package com.citytaxi.controller.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Local implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4357926038082810788L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer x;
	
	private Integer y;
	
	@OneToMany
	@JoinColumn
	private List<Taxista> taxistas;
	
	@OneToMany
	@JoinColumn
	private List<Passageiro> passageiros;
	
	private LocalStatus status;
	
	@Column(name="tempo")
	private Integer tempo;
	
	public Local() {
		super();
	}
	
	public Local(int y, int x, Integer tempo) {
		super();
		this.x = x;
		this.y = y;
		this.tempo = tempo;
	}
	
	public Local(int y, int x, LocalStatus status) {
		super();
		this.x = x;
		this.y = y;
		this.taxistas = new ArrayList<Taxista>();
		this.passageiros = new ArrayList<Passageiro>();
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Taxista> getTaxistas() {
		return taxistas;
	}

	public void setTaxistas(List<Taxista> taxistas) {
		this.taxistas = taxistas;
	}

	public List<Passageiro> getPassageiros() {
		return passageiros;
	}

	public void setPassageiros(List<Passageiro> passageiros) {
		this.passageiros = passageiros;
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
	
	public String getCurrentStatus() {
		String result = "";
		if(status.equals(LocalStatus.bloqueada)) {
			result = LocalStatus.bloqueada.getStatus();
		}else if(taxistas.isEmpty() && passageiros.isEmpty()) {
			result = LocalStatus.vazia.getStatus();
		}else {
			for(Taxista taxista : taxistas) {
				result = result + taxista.getStatus().getStatus() + " ";
			}
			for(int i =0; i< passageiros.size(); i++) {
				result = result + "p ";
			}
			result = result.trim();
		}
		return result;
	}

	@Override
	public String toString() {
		return "Local [x=" + x + ", y=" + y + "status=" + status + ", taxistas=" + taxistas
				+ ", passageiros=" + passageiros + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		Local other = (Local) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
}
