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
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.lace.converter.ConverterList;
import com.lace.entity.PacientesEntity;
import com.lace.model.PacientesModel;
import com.lace.model.json.RFCModel;
import com.lace.repository.PacientesRepository;

@Service("pacientesService")
public class PacientesService {

	@Autowired
	@Qualifier("pacientesRepository")
	private PacientesRepository pRepo;

	@Autowired
	@Qualifier("converterList")
	private ConverterList converterList;

	public ResponseEntity<?> crear(PacientesModel pm) throws Exception {

		Map<String, Object> response = new HashMap<>();
		ResponseEntity<?> p = buscaNombrePaciente(pm.getNombre());

		if (p != null && (p.getStatusCodeValue() == 500 || p.getStatusCodeValue() == 400)) {
			return p;
		}

		// validaCampos(pm);

		RFCModel rfcM = new RFCModel(pm.getRfcjson());
		Gson gson = new Gson();
		String rfcG = gson.toJson(rfcM);
		String telJ = gson.toJson(pm.getTelefonos());

		PacientesEntity pe = new PacientesEntity(pm);
		pe.setJsonRFC(rfcG);
		pe.setJsonTelefono(telJ);

		try {
			pRepo.save(pe);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al crear en la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Paciente creado con éxito");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> listaPacientes(Pageable pageable, String nombre) throws Exception {

		if (nombre == null)
			nombre = "%";

		List<PacientesEntity> lP = null;
		Map<String, Object> response = new HashMap<>();

		try {
			lP = pRepo.findAll(pageable, nombre).getContent();
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		List<PacientesModel> lPM = converterList.listaPacientes(lP);
		response.put("body", lPM);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		response.put("mensaje", "Lista de pacientes");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // ResponseEntity.ok(em);

	}

	/*
	 * public ResponseEntity<List<PacientesModel>> listaPaciente(String name) throws
	 * Exception { List<PacientesEntity> paciente = pRepo.findByNombreLike(name);
	 * return null; }
	 */
	public ResponseEntity<?> obtenerPaciente(int id) throws Exception {

		ResponseEntity<?> pe = buscaPaciente(id);

		if (pe.getStatusCodeValue() == 404 || pe.getStatusCodeValue() == 500) {
			return pe;
		}

		return pe;
	}

	public ResponseEntity<?> borraPaciente(int id) throws Exception {

		ResponseEntity<?> bp = buscaPaciente(id);
		Map<String, Object> response = new HashMap<>();

		if (bp.getStatusCodeValue() == 404 || bp.getStatusCodeValue() == 500) {
			return bp;
		}

		try {
			pRepo.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al eliminar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Paciente <" + id + "> eliminado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> modificaPaciente(PacientesModel pm, int id) throws Exception {

		Map<String, Object> response = new HashMap<>();

		ResponseEntity<?> bp = buscaPaciente(id);

		if (bp.getStatusCodeValue() == 404 || bp.getStatusCodeValue() == 500) {
			return bp;
		}

		// validaCampos(pm);

		RFCModel rfcM = new RFCModel(pm.getRfcjson());
		Gson gson = new Gson();
		String rfcG = gson.toJson(rfcM);
		String telJ = gson.toJson(pm.getTelefonos());

		PacientesEntity pe = new PacientesEntity(pm);
		pe.setId(id);
		pe.setJsonRFC(rfcG);
		pe.setJsonTelefono(telJ);

		try {
			pRepo.save(pe);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Paciente <" + pm.getNombre() + "> modificado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	public Long total(String nombre) throws Exception {
		if (nombre == null)
			nombre = "%";
		return pRepo.count(nombre);
	}

	private ResponseEntity<?> buscaPaciente(int id) {

		Map<String, Object> response = new HashMap<>();

		PacientesEntity pe = null;
		try {
			pe = pRepo.findById(id);

		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (pe == null) {
			response.put("mensaje", "El paciente no se encuentra con ID <" + id + ">");
			response.put("statusCodeValue", HttpStatus.NOT_FOUND.value());
			response.put("statusCode", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}

		PacientesModel pm = new PacientesModel(pe);
		response.put("body", pm);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	private void validaCampos(PacientesModel pm) {
		if (pm.getNombre() == null || pm.getNombre().isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay nombre de paciente");

	}

	private ResponseEntity<?> buscaNombrePaciente(String nombre) {

		Map<String, Object> response = new HashMap<>();

		PacientesEntity pe = null;

		try {
			pe = pRepo.findByNombre(nombre);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (pe != null) {
			response.put("mensaje", "El paciente con el nombre <" + nombre + "> ya existe.");
			response.put("statusCodeValue", HttpStatus.BAD_REQUEST.value());
			response.put("statusCode", "BAD_REQUEST");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		return null;
	}

	/*
	 * Seguro se utiliza fuera de este servicio
	 */
	public ResponseEntity<?> buscaPacienteId(int id) {

		Map<String, Object> response = new HashMap<>();
		PacientesEntity pe = null;

		try {
			pe = pRepo.findById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (pe == null) {
			response.put("mensaje", "El Paciente no se encuentra con ID <" + id + ">");
			response.put("statusCodeValue", HttpStatus.NOT_FOUND.value());
			response.put("statusCode", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}

		response.put("body", pe);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		ResponseEntity<PacientesEntity> pe2 = null;

		// pe2 = pe;

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	public PacientesEntity pacienteE(int id) {
		PacientesEntity pe = null;
		pe = pRepo.findById(id);
		
		return pe;
	}

}
