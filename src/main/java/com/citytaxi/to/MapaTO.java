package com.citytaxi.to;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.Mapa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class MapaTO {

	private Integer tempo;
	private List<String> ruas;
	
	public MapaTO() {
		super();
		this.ruas = new ArrayList<String>();
	}
	
	public static MapaTO fromEntity(Mapa mapa) {
		MapaTO mapaTO = new MapaTO();
		if(mapa != null) {
			mapaTO.tempo = mapa.getTempo();
			Local[][] locais = mapa.getLocais();
			for(int i =0; i< locais.length; i++){
				String linha = "";
				for(int j=0; j<locais[i].length; j++) {
					linha += locais[i][j].getCurrentStatus();
				}
				mapaTO.ruas.add(linha);
			}
		}
		return mapaTO;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public List<String> getRuas() {
		return ruas;
	}

	public void setRuas(List<String> ruas) {
		this.ruas = ruas;
	}

}
