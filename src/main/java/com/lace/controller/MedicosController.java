package com.lace.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lace.model.MedicosModel;
import com.lace.services.MedicosService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE})
@Secured("ROLE_USUARIO")
public class MedicosController {
	
	@Autowired
	@Qualifier("medicosService")
	private MedicosService service;
	
	private static final Log LOG = LogFactory.getLog(MedicosController.class);

	/*
	 * Crea Médico
	 */
	@PostMapping(value = "/medicos")
	public ResponseEntity<?> crear(@RequestBody @Valid MedicosModel m) throws Exception {
		LOG.info("METHOD: POST --> /medicos");
		return service.crear(m);
	}
	
	/*
	 * Obtiene Médico
	 */
	@GetMapping(value = "/medicos/{id}")
	public ResponseEntity<?> obtiene(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: GET --> /medicos/" + id);
		return service.obtenerMedico(id);
	}
	
	/*
	 * Obtiene Médicos
	 */
	@GetMapping(value = "/medicos")
	public ResponseEntity<?> listaMedicos(Pageable pageable,
			 @RequestParam(name = "name", required = false) String name) throws Exception {
		LOG.info("METHOD: GET --> /medicos");
		
		return service.obtenerMedicos(pageable, name);
	}
	
	/*
	 * Modifica Médico
	 */
	@PutMapping(value = "/medicos/{id}")
	public ResponseEntity<?> modifica(@RequestBody @Valid MedicosModel m, @PathVariable("id") int id)
		throws Exception {
		LOG.info("METHOD: PUT --> /medicos/" + id);
		
		return service.modifica(m, id);
	}
	
	/*
	 * Elimina Medico
	 */
	@DeleteMapping(value = "/medicos/{id}")
	public ResponseEntity<?> borra(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: PUT --> /medicos/" + id);
		return service.borra(id);
	}
	
	/*
	 * Obtener total Medico
	 */
	@GetMapping(value = "/medicos/total")
	public Long total(@RequestParam(name = "name", required = false) String name) throws Exception{
		return service.total(name);
	}
	
}
