package com.lace.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.lace.model.UsuariosModel;
import com.lace.services.UsuariosService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@Secured("ROLE_USUARIO")
public class UsuariosController {

	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService uService;

	private static final Log LOG = LogFactory.getLog(UsuariosController.class);

	@PostMapping(value = "/usuarios")
	public ResponseEntity<?> crear(@RequestBody @Valid UsuariosModel um) throws Exception {
		LOG.info("METHOD: POST --> /usuarios");
		return uService.crear(um);
	}

	@GetMapping(value = "/usuarios")
	public ResponseEntity<?> listaUsuarios(Pageable pageable,
			@RequestParam(name = "name", required = false) String name) throws Exception {
		LOG.info("METHOD: GET --> /usuarios");
		return uService.listaUsuarios(pageable, name);
	}

	@GetMapping(value = "/usuarios/{id}")
	public ResponseEntity<?> obtieneUsuario(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: GET --> /usuarios/{id}");
		return uService.obtenerUsuario(id);
	}

	@DeleteMapping(value = "/usuarios/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id) throws Exception {
		LOG.info("METHOD: DELETE --> /usuarios/{id}");
		
		return uService.borraUsuario(id);
	}

	@PutMapping(value = "/usuarios/{id}")
	public ResponseEntity<?> editar(@RequestBody @Valid UsuariosModel um, @PathVariable("id") int id) throws Exception {

		LOG.info("METHOD: PUT --> /usuarios/" + id);
		return uService.actualizaUsuario(um, id);
	}

	/*
	 * Obtener total Usuarios
	 */
	@GetMapping(value = "/usuarios/total")
	public Long total(@RequestParam(name = "name", required = false) String nombre) throws Exception {
		return uService.total(nombre);
	}
}
