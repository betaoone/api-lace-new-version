package com.lace.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lace.model.AnalisisModel;
import com.lace.services.AnalisisService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@Secured("ROLE_USUARIO")
public class AnalisisController {

	@Autowired
	@Qualifier("analisisService")
	private AnalisisService service;

	@GetMapping(value = "/pacientes/{idpaciente}/analisis/{idanalisis}")
	public ResponseEntity<?> unAnalisis(@PathVariable("idpaciente") int idpaciente,
			@PathVariable("idanalisis") int idanalisis) throws Exception {

		return service.unAnalisis(idpaciente, idanalisis);
	}

	@GetMapping(value = "/pacientes/{id}/analisis")
	public ResponseEntity<?> lista(Pageable pageable, @PathVariable("id") int id) throws Exception {

		return service.lista(pageable, id);
	}

	@PostMapping(value = "/pacientes/{id}/analisis")
	public ResponseEntity<?> crear(@RequestBody @Valid AnalisisModel am, @PathVariable("id") int id) throws Exception {
	
		return service.crear(am, id);
	}

	@PutMapping(value = "/pacientes/{idpaciente}/analisis/{idanalisis}")
	public ResponseEntity<?> modifica(@PathVariable("idpaciente") int idpaciente,
			@PathVariable("idanalisis") int idanalisis, @RequestBody @Valid AnalisisModel am) throws Exception {

		return service.modifica(idpaciente, idanalisis, am);
	}
	
	@DeleteMapping(value = "/pacientes/{idpaciente}/analisis/{idanalisis}")
	public ResponseEntity<?> elimina(@PathVariable("idpaciente") int idpaciente,
			@PathVariable("idanalisis") int idanalisis) throws Exception {
		return service.elimina(idpaciente, idanalisis);
	}
	
	@GetMapping(value = "/pacientes/{idpaciente}/analisis/total")
	public Long total(@PathVariable("idpaciente") int id) throws Exception {
		return service.total(id);
	}
}
