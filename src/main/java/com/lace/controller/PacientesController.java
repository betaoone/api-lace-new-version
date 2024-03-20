package com.lace.controller;


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

import com.lace.model.PacientesModel;
import com.lace.services.PacientesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@Secured("ROLE_USUARIO")
public class PacientesController {

	@Autowired
	@Qualifier("pacientesService")
	private PacientesService pService;

	private static final Log LOG = LogFactory.getLog(PacientesController.class);

	/*
	 * Crea Paciente
	 */
	@PostMapping(value = "/pacientes")
	public ResponseEntity<?> crear(@RequestBody @Valid PacientesModel p) throws Exception {
		LOG.info("METHOD: POST --> /pacientes");

		return pService.crear(p);
	}

	/*
	 * Obtiene Paciente
	 */
	@GetMapping(value = "/pacientes/{id}")
	public ResponseEntity<?> obtienePaciente(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: GET --> /pacientes/" + id);
		return pService.obtenerPaciente(id);
	}

	/*
	 * Obtiene Pacientes
	 */
	@GetMapping(value = "/pacientes")
	public ResponseEntity<?> listaPacientes(Pageable pageable,
			@RequestParam(name = "name", required = false) String name) throws Exception {
		LOG.info("METHOD: GET --> /pacientes");
		return pService.listaPacientes(pageable, name);
	}
	
	/*
	 * Obtener total pacientes
	 */
	@GetMapping(value = "/pacientes/total")
	public Long total(@RequestParam(name = "name", required = false) String name) throws Exception {
		return pService.total(name);
	}

	/*
	 * Obtiene pacientes pero con parametro de nombre
	 * 
	 * public ResponseEntity<List<PacientesModel>>
	 * listaPaciente(@RequestParam("name") String name) throws Exception {
	 * LOG.info("METHOD: GET --> /pacientes?name=" + name); return
	 * pService.listaPaciente(name); }
	 * 
	 * /* Borra Paciente
	 */
	@DeleteMapping(value = "/pacientes/{id}")
	public ResponseEntity<?> eliminaPaciente(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: DELETE --> /pacientes/" + id);
		return pService.borraPaciente(id);
	}

	/*
	 * Modifica Paciente
	 */
	@PutMapping(value = "/pacientes/{id}")
	public ResponseEntity<?> actualizaPaciente(@RequestBody @Valid PacientesModel pm, @PathVariable("id") int id)
			throws Exception {
		LOG.info("METHOD: PUT --> /pacientes/" + id);
		return pService.modificaPaciente(pm, id);

	}



}
