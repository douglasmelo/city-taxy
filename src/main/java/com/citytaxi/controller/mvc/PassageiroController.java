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

import com.citytaxi.controller.domain.Passageiro;
import com.citytaxi.service.PassageiroBo;
import com.citytaxi.to.PassageiroTO;
import com.citytaxi.to.ResultObjectErrorTO;

@Controller
@RequestMapping(value="/passageiro")
public class PassageiroController {

	protected static final String PRODUCES_JSON = MediaType.APPLICATION_JSON + ";charset=UTF-8";
	
	@Autowired
	private PassageiroBo passageiroBo;

	/**
	 * Find passenger by id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> findById(@PathVariable("id") Integer id){
		try {
			Passageiro result = passageiroBo.findById(id);
			PassageiroTO passageiroTO = PassageiroTO.fromEntity(result);
			return new ResponseEntity<Object>(passageiroTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Create new passenger with payload information
	 * @param payload
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = PRODUCES_JSON, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody Passageiro payload){
		try {
			Passageiro result = passageiroBo.create(payload);
			PassageiroTO passageiroTO = PassageiroTO.fromEntity(result);
			return new ResponseEntity<Object>(passageiroTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Update new passenger with payload information
	 * @param payload
	 * @return
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = PRODUCES_JSON, produces = PRODUCES_JSON)
	@ResponseBody
	public ResponseEntity<Object> update(@RequestBody Passageiro payload){
		try {
			passageiroBo.update(payload);
			PassageiroTO passageiroTO = PassageiroTO.fromEntity(payload);
			return new ResponseEntity<Object>(passageiroTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new ResultObjectErrorTO("Erro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
