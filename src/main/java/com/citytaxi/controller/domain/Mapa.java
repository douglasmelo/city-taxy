package com.citytaxi.controller.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "tempo"))
public class Mapa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4254668755021571933L;

	@Id
	@GeneratedValue
	private Integer id;

	@Lob
	private Local[][] locais;
	
	@Column(name="tempo")
	private Integer tempo;
	
	public Mapa() {
		super();
	}
	
	public Mapa(Local[][] mapa, Integer tempo) {
		super();
		this.locais = mapa;
		this.tempo = tempo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Local[][] getLocais() {
		return locais;
	}

	public void setLocais(Local[][] locais) {
		this.locais = locais;
	}
	
}
