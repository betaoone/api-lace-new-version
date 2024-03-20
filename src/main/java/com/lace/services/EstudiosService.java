package com.lace.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.lace.converter.ConverterList;
import com.lace.entity.EstudiosEntity;
import com.lace.model.EstudiosModel;
import com.lace.repository.EstudiosRepository;

@Service("estudiosService")
public class EstudiosService {

	@Autowired
	@Qualifier("estudiosRepository")
	private EstudiosRepository repository;

	@Autowired
	@Qualifier("converterList")
	private ConverterList converter;

	public ResponseEntity<?> crear(EstudiosModel e) throws Exception {

		Map<String, Object> response = new HashMap<>();
		ResponseEntity<?> a = buscaNombreEstudio(e.getNombre());

		if (a != null && (a.getStatusCodeValue() == 400 || a.getStatusCodeValue() == 500)) {
			return a;
		}

		Gson gson = new Gson();

		String json_estudio = gson.toJson(e.getJson_estudio());
		EstudiosEntity ee = new EstudiosEntity(e.getNombre(), json_estudio);

		try {
			repository.save(ee);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al crear en la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Estudio creado con éxito");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> obtener(int id) throws Exception {

		ResponseEntity<?> be = buscaEstudioId(id);

		System.out.println(be.getStatusCode());

		if (be.getStatusCodeValue() == 404 || be.getStatusCodeValue() == 500) {
			return be;
		}
		

		return be;//ResponseEntity.ok(be);
	}

	public ResponseEntity<List<EstudiosModel>> obtenerNombre(String nombre) throws Exception {
		return ResponseEntity.ok(buscaN(nombre));
	}

	public ResponseEntity<?> lista(Pageable pageable, String nombre) throws Exception {

		if (nombre == null)
			nombre = "%";

		List<EstudiosEntity> ee = null;
		Map<String, Object> response = new HashMap<>();

		try {
			ee = repository.findAll(pageable, nombre).getContent();
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<EstudiosModel> em = converter.listaEstudios(ee);
		response.put("body", em);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); //ResponseEntity.ok(em);
	}

	public ResponseEntity<?> modifica(EstudiosModel e, int id) throws Exception {

		Map<String, Object> response = new HashMap<>();

		ResponseEntity<?> be = buscaEstudioId(id);

		if (be.getStatusCodeValue() == 404 || be.getStatusCodeValue() == 500) {
			return be;
		}

		Gson gson = new Gson();

		String json_estudio = gson.toJson(e.getJson_estudio());

		EstudiosEntity ee = new EstudiosEntity(e.getNombre(), json_estudio);
		ee.setId(id);

		try {
			repository.save(ee);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Estudio <" + e.getNombre() + "> modificado correctamente.");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> borra(int id) throws Exception {
		
		ResponseEntity<?> be = buscaEstudioId(id);

		if (be.getStatusCodeValue() == 404 || be.getStatusCodeValue() == 500) {
			return be;
		}

		
		Map<String, Object> response = new HashMap<>();

		try {
			repository.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al eliminar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Estudio <" + id + "> eliminado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public Long total(String name) throws Exception {
		if (name == null)
			name = "%";
		return repository.count(name);
	}

	private List<EstudiosModel> buscaN(String nombre) {
		List<EstudiosEntity> e = repository.findByNombrelike(nombre);

		List<EstudiosModel> em = converter.listaEstudios(e);

		return em;
	}

	private ResponseEntity<?> buscaNombreEstudio(String nombre) {

		Map<String, Object> response = new HashMap<>();
		EstudiosEntity e = null;

		try {
			e = repository.findByNombre(nombre);

		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (e != null) {
			response.put("mensaje", "El estudio con el nombre <" + nombre + "> ya existe.");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		return null;

	}

	private ResponseEntity<?> buscaEstudioId(int id) {

		Map<String, Object> response = new HashMap<>();
		EstudiosEntity e = null;

		try {
			e = repository.findById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (e == null) {
			response.put("mensaje", "El estudio no se encuentra con ID <" + id + ">");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		EstudiosModel em = new EstudiosModel(e);
		response.put("body", em);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);//ResponseEntity.ok(em);
	}

	/*
	 * private EstudiosModel buscaEstudioId(int id) { EstudiosEntity e =
	 * repository.findById(id);
	 * 
	 * if (e == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	 * "No se encuentra el estudio <" + id + ">.");
	 * 
	 * EstudiosModel em = new EstudiosModel(e);
	 * 
	 * return em; }
	 */
}
