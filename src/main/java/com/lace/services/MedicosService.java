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
import com.lace.entity.MedicosEntity;
import com.lace.entity.PacientesEntity;
import com.lace.model.MedicosModel;
import com.lace.model.json.HospitalModel;
import com.lace.repository.MedicosRepository;

@Service("medicosService")
public class MedicosService {

	@Autowired
	@Qualifier("medicosRepository")
	private MedicosRepository mRepo;

	@Autowired
	@Qualifier("converterList")
	private ConverterList converter;

	public ResponseEntity<?> crear(MedicosModel m) throws Exception {

		Map<String, Object> response = new HashMap<>();

		ResponseEntity<?> rm = buscaNombreMedico(m.getNombre());

		if (rm != null && (rm.getStatusCodeValue() == 500 || rm.getStatusCodeValue() == 400)) {
			return rm;
		}
		// validaCampos(m);

		HospitalModel hm = new HospitalModel(m.getJsonInfoHosp());
		Gson gson = new Gson();

		String hospital = gson.toJson(hm);
		String tel = gson.toJson(m.getJsonTel());

		MedicosEntity me = new MedicosEntity(m);
		me.setJsonInfoHosp(hospital);
		me.setJsonTel(tel);

		try {
			mRepo.save(me);
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

	public ResponseEntity<?> obtenerMedico(int id) throws Exception {

		ResponseEntity<?> me = buscaMedicoId(id);

		if (me.getStatusCodeValue() == 404 || me.getStatusCodeValue() == 500) {
			return me;
		}

		return me;// ResponseEntity.ok(buscaMedicoId(id));

	}

	public ResponseEntity<?> obtenerMedicos(Pageable pageable, String nombre) throws Exception {

		if (nombre == null)
			nombre = "%";

		List<MedicosEntity> me = null;
		Map<String, Object> response = new HashMap<>();

		try {
			me = mRepo.findAll(pageable, nombre).getContent();
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		List<MedicosModel> mm = converter.listaMedicos(me);
		response.put("body", mm);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		response.put("mensaje", "Lista de médicos");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // ResponseEntity.ok(em);
	}

	public ResponseEntity<?> modifica(MedicosModel m, int id) throws Exception {

		Map<String, Object> response = new HashMap<>();
		ResponseEntity<?> bm = buscaMedicoId(id);

		if (bm.getStatusCodeValue() == 404 || bm.getStatusCodeValue() == 500) {
			return bm;
		}
//		validaCampos(m);

		HospitalModel hm = new HospitalModel(m.getJsonInfoHosp());
		Gson gson = new Gson();

		String hospital = gson.toJson(hm);
		String tel = gson.toJson(m.getJsonTel());

		MedicosEntity me = new MedicosEntity(m);
		me.setJsonInfoHosp(hospital);
		me.setJsonTel(tel);
		me.setId(id);

		try {
			mRepo.save(me);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Médico <" + m.getNombre() + "> modificado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	public ResponseEntity<?> borra(int id) throws Exception {

		ResponseEntity<?> bm = buscaMedicoId(id);
		Map<String, Object> response = new HashMap<>();

		if (bm.getStatusCodeValue() == 404 || bm.getStatusCodeValue() == 500) {
			return bm;
		}

		try {
			mRepo.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al eliminar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Médico <" + id + "> eliminado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public Long total(String name) throws Exception {
		if (name == null)
			name = "%";
		return mRepo.count(name);
	}

	private ResponseEntity<?> buscaNombreMedico(String nombre) {

		Map<String, Object> response = new HashMap<>();

		MedicosEntity m = null;

		try {
			m = mRepo.findByNombre(nombre);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (m != null) {
			response.put("mensaje", "El médico con el nombre <" + nombre + "> ya existe.");
			response.put("statusCodeValue", HttpStatus.BAD_REQUEST.value());
			response.put("statusCode", "BAD_REQUEST");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		return null;
	}

	private void validaCampos(MedicosModel m) {

		if (m.getNombre() == null || m.getNombre().isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay nombre de Médico");

		if (m.getEmail() == null || m.getEmail().isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay email de Médico");

	}

	private ResponseEntity<?> buscaMedicoId(int id) {

		Map<String, Object> response = new HashMap<>();
		MedicosEntity me = null;

		try {
			me = mRepo.findById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (me == null) {
			response.put("mensaje", "El médico no se encuentra con ID <" + id + ">");
			response.put("statusCodeValue", HttpStatus.NOT_FOUND.value());
			response.put("statusCode", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}

		MedicosModel mm = new MedicosModel(me);
		response.put("body", mm);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> buscaMedico(String nombre) {

		Map<String, Object> response = new HashMap<>();
		MedicosEntity m = null;

		try {
			m = mRepo.findByNombre(nombre);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (m == null) {
			response.put("mensaje", "El Médico <" + nombre + "> no se encuentra.");
			response.put("statusCodeValue", HttpStatus.NOT_FOUND.value());
			response.put("statusCode", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
			
		response.put("body", m);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	public MedicosEntity medicosE(String nombre) {
		MedicosEntity me = null;
		me = mRepo.findByNombre(nombre);
		return me;
	}

}