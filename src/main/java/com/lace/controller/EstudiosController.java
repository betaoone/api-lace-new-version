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

import com.lace.model.EstudiosModel;
import com.lace.services.EstudiosService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE})
@Secured("ROLE_USUARIO")
public class EstudiosController {
	
	@Autowired
	@Qualifier("estudiosService")
	private EstudiosService service;
	
	private static final Log LOG = LogFactory.getLog(EstudiosController.class);

	/*
	 * Crea Estudio
	 */
	@PostMapping(value = "/estudios")
	public ResponseEntity<?> crear(@RequestBody @Valid EstudiosModel e) throws Exception {
		LOG.info("METHOD: POST --> /estudios");
		return service.crear(e);
	}
	
	/*
	 * Obtiene Estudio
	 */
	@GetMapping(value = "/estudios/{id}")
	public ResponseEntity<?> obtiene(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: GET --> /estudios/" + id);

		return service.obtener(id);
	}
	
	/*
	 * Obtiene Estudios
	 */
	@GetMapping(value = "/estudios")
	public ResponseEntity<?> lista(Pageable pageable,
			@RequestParam(name = "name", required = false) String name) throws Exception {
		LOG.info("METHOD: GET --> /estudios");
		return service.lista(pageable, name);
	}
	
	/*
	 * Modifica Estudio
	 */
	@PutMapping(value = "/estudios/{id}")
	public ResponseEntity<?> editar(@RequestBody @Valid EstudiosModel e, @PathVariable("id") int id)
		throws Exception {
		LOG.info("METHOD: PUT --> /estudios/"+id);
		return service.modifica(e, id);
	}
	
	/*
	 * Elimina Estudio
	 */
	@DeleteMapping(value = "/estudios/{id}")
	public ResponseEntity<?> borra(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: DELETE --> /estudios/"+id);

		return service.borra(id);
		
	}
	
	/*
	 * Obtener total Estudio
	 */
	@GetMapping(value = "/estudios/total")
	public Long total(@RequestParam(name = "name", required = false) String name) throws Exception{
		return service.total(name);
	}
	
	/*
	 * Obtener por nombre
	 */
	@GetMapping(value ="/estudios/nombre")
	public ResponseEntity<List<EstudiosModel>> obtieneNombre(@RequestParam("nombre") String nombre) throws Exception {
		
		return service.obtenerNombre(nombre);
	}
}
