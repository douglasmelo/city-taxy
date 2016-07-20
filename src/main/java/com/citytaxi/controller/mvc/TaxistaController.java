package com.citytaxi.controller.mvc;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citytaxi.controller.domain.Taxista;
import com.citytaxi.service.TaxistaBo;
import com.citytaxi.to.ResultObjectErrorTO;
import com.citytaxi.to.TaxistaTO;

@Controller
@RequestMapping(value="/taxista")
public class TaxistaController {
	
	protected static final String PRODUCES_JSON = MediaType.APPLICATION_JSON + ";charset=UTF-8";
	
	@Autowired
	private TaxistaBo taxistaBo;

	/**
	 * Find Taxista by id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> findById(@PathVariable("id") Integer id){
		try {
			Taxista result = taxistaBo.findById(id);
			TaxistaTO taxistaTO = TaxistaTO.fromEntity(result);
			return new ResponseEntity<Object>(taxistaTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Create new taxista with payload information
	 * @param payload
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = PRODUCES_JSON, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody Taxista payload){
		try {
			Taxista result = taxistaBo.save(payload);
			TaxistaTO taxistaTO = TaxistaTO.fromEntity(result);
			return new ResponseEntity<Object>(taxistaTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Update new taxista with payload information
	 * @param payload
	 * @return
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = PRODUCES_JSON, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> update(@RequestBody Taxista payload){
		try {
			taxistaBo.update(payload);
			TaxistaTO taxistaTO = TaxistaTO.fromEntity(payload);
			return new ResponseEntity<Object>(taxistaTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
