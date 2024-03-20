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
import com.lace.entity.AnalisisEntity;
import com.lace.entity.MedicosEntity;
import com.lace.entity.PacientesEntity;
import com.lace.model.AnalisisModel;
import com.lace.repository.AnalisisRepository;

@Service("analisisService")
public class AnalisisService {

	@Autowired
	@Qualifier("analisisRepository")
	private AnalisisRepository repository;

	@Autowired
	@Qualifier("converterList")
	private ConverterList converterList;

	@Autowired
	@Qualifier("pacientesService")
	private PacientesService pr;

	@Autowired
	@Qualifier("medicosService")
	private MedicosService mService;

	public ResponseEntity<?> lista(Pageable p, int id) throws Exception {

		Map<String, Object> response = new HashMap<>();
		List<AnalisisEntity> ae = null;
		List<AnalisisModel> am = null;

		ResponseEntity<?> ps = pr.buscaPacienteId(id);
		if (ps != null && (ps.getStatusCodeValue() == 500 || ps.getStatusCodeValue() == 404)) {
			return ps;
		}

		try {
			ae = repository.findAll(p, id).getContent();
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		am = converterList.listaAnaisis(ae);
		response.put("body", am);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		response.put("mensaje", "Lista de Análisis");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // ResponseEntity.ok(em);
	}

	public ResponseEntity<?> unAnalisis(int idpaciente, int idanalisis) throws Exception {

		AnalisisEntity ae = null;
		Map<String, Object> response = new HashMap<>();

		ResponseEntity<?> ps = pr.buscaPacienteId(idpaciente);
		if (ps != null && (ps.getStatusCodeValue() == 500 || ps.getStatusCodeValue() == 404)) {
			return ps;
		}

		try {
			ae = repository.findByIdAndId_pacientes(idanalisis, idpaciente);
			//System.out.println(ae.getFecha().toString());
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (ae == null) {
			response.put("mensaje", "El Análisis no se encuentra con ID <" + idanalisis + ">");
			response.put("statusCodeValue", HttpStatus.NOT_FOUND.value());
			response.put("statusCode", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}

		AnalisisModel am = new AnalisisModel(ae);
		response.put("body", am);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // ResponseEntity.ok(em);
	}

	public ResponseEntity<?> crear(AnalisisModel am, int id) throws Exception {

		Map<String, Object> response = new HashMap<>();
		PacientesEntity pe = null;
		MedicosEntity m = null;

		ResponseEntity<?> ps = pr.buscaPacienteId(id);
		if (ps != null && (ps.getStatusCodeValue() == 500 || ps.getStatusCodeValue() == 404)) {
			return ps;
		}

		if (ps != null) {
			pe = pr.pacienteE(id);
		}

		ResponseEntity<?> me = mService.buscaMedico(am.getMedico());
		if (me != null && (me.getStatusCodeValue() == 500 || me.getStatusCodeValue() == 404)) {
			return me;
		}

		if (me != null) {
			m =  mService.medicosE(am.getMedico());
		}

		Gson gson = new Gson();
		
		
		String json = gson.toJson(am.getJson());
		//System.out.println(json);

		AnalisisEntity ae = new AnalisisEntity(am);
		System.out.println(json);
		
		ae.setJson(json);
		ae.setPacientesEntity(pe);
		ae.setMedicosEntity(m);

		try {
			repository.save(ae);
		} catch (DataAccessException ex) {
		response.put("mensaje", "Error al crear recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("mensaje", "Análisis creado con éxito");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> modifica(int idpaciente, int idanalisis, AnalisisModel am) throws Exception {

		Map<String, Object> response = new HashMap<>();
		PacientesEntity pe = null;
		MedicosEntity m = null;

		ResponseEntity<?> ps = pr.buscaPacienteId(idpaciente);
		if (ps != null && (ps.getStatusCodeValue() == 500 || ps.getStatusCodeValue() == 404)) {
			return ps;
		}

		if (ps != null) {
			pe = pr.pacienteE(idpaciente);//(PacientesEntity) ps.getBody();
		}

		ResponseEntity<?> me = mService.buscaMedico(am.getMedico());
		if (me != null && (me.getStatusCodeValue() == 500 || me.getStatusCodeValue() == 404)) {
			return me;
		}

		if (me != null) {
			m = mService.medicosE(am.getMedico());//(MedicosEntity) me.getBody();
		}

		Gson gson = new Gson();

		String json = gson.toJson(am.getJson());

		AnalisisEntity ae = new AnalisisEntity(am);
		ae.setId(idanalisis);
		ae.setPacientesEntity(pe);
		ae.setMedicosEntity(m);
		ae.setJson(json);

		try {
			repository.save(ae);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Análisis <" + idanalisis + "> modificado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> elimina(int idpaciente, int idanalisis) throws Exception {

		Map<String, Object> response = new HashMap<>();

		ResponseEntity<?> ps = pr.buscaPacienteId(idpaciente);
		if (ps != null && (ps.getStatusCodeValue() == 500 || ps.getStatusCodeValue() == 404)) {
			return ps;
		}

		try {
			repository.deleteById(idanalisis);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al eliminar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Análisis <" + idanalisis + "> eliminado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public Long total(int id) throws Exception {
		return repository.count(id);
	}

}
