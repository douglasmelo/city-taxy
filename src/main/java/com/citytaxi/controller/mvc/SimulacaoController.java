package com.citytaxi.controller.mvc;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citytaxi.controller.domain.Mapa;
import com.citytaxi.service.LocalBo;
import com.citytaxi.service.MapaBo;
import com.citytaxi.service.PassageiroBo;
import com.citytaxi.service.SimulacaoBo;
import com.citytaxi.service.TaxistaBo;
import com.citytaxi.to.MapaTO;
import com.citytaxi.to.ResultObjectErrorTO;

@Controller
@RequestMapping(value="/simulacao")
public class SimulacaoController {

	protected static final String PRODUCES_JSON = MediaType.APPLICATION_JSON + ";charset=UTF-8";
	
	@Autowired
	private MapaBo mapaBo;
	
	@Autowired
	private PassageiroBo passageiroBo;
	
	@Autowired
	private TaxistaBo taxistaBo;
	
	@Autowired
	private LocalBo localBo;
	
	@Autowired
	private SimulacaoBo simulacaoBo;
	
	@RequestMapping(value ="/avancar", method = RequestMethod.GET, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> avancarTempo(){
		try {
			Mapa mapa = simulacaoBo.avancar();
			MapaTO mapaTO = MapaTO.fromEntity(mapa);
			return new ResponseEntity<Object>(mapaTO, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="/estadoatual", method = RequestMethod.GET, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> estadoAtual(){
		try {
			Mapa mapa = simulacaoBo.estadoAtual();
			MapaTO mapaTO = MapaTO.fromEntity(mapa);
			return new ResponseEntity<Object>(mapaTO, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="/reiniciar", method = RequestMethod.GET, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> reiniciarSimulacao(){
		try {
			mapaBo.deleteAll();
			taxistaBo.deleteAll();
			passageiroBo.deleteAll();
			localBo.deleteAll();
			return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
