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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lace.converter.ConverterList;
import com.lace.entity.UsuariosEntity;
import com.lace.model.UsuariosModel;
import com.lace.repository.UsuariosRepository;

@Service("usuariosService")
public class UsuariosService {

	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository uRepository;

	@Autowired
	@Qualifier("converterList")
	private ConverterList converterList;

	@Autowired
	private PasswordEncoder encoder;

	/*
	 * Crea usuario
	 */
	public ResponseEntity<?> crear(UsuariosModel uModel) throws Exception {

		Map<String, Object> response = new HashMap<>();
		ResponseEntity<?> ru = buscaNombreUsuario(uModel.getNombreUsuario());

		if (ru != null && (ru.getStatusCodeValue() == 500 || ru.getStatusCodeValue() == 400)) {
			return ru;
		}
		// validaCampos(uModel);

		UsuariosEntity uEntity = new UsuariosEntity(uModel);
		uEntity.setContrasena(encoder.encode(uModel.getContrasena()));

		try {
			uRepository.save(uEntity);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al crear en la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Usuario creado con éxito");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/*
	 * Busca Lista de usuarios
	 */
	public ResponseEntity<?> listaUsuarios(Pageable pageable, String name) throws Exception {

		if (name == null)
			name = "%";

		List<UsuariosEntity> uE = null;
		Map<String, Object> response = new HashMap<>();

		try {
			uE = uRepository.findAll(pageable, name).getContent();
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<UsuariosModel> uM = converterList.listaUsuarios(uE);
		response.put("body", uM);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		response.put("mensaje", "Lista de usuarios");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // ResponseEntity.ok(em);
	}

	/*
	 * Busca usuario
	 */
	public ResponseEntity<?> obtenerUsuario(int id) throws Exception {

		ResponseEntity<?> u = buscaUsuario(id);

		if (u.getStatusCodeValue() == 404 || u.getStatusCodeValue() == 500) {
			return u;
		}
		return u;
	}

	/*
	 * Elimina usuario
	 */
	public ResponseEntity<?> borraUsuario(int id) throws Exception {

		ResponseEntity<?> bu = buscaUsuario(id);
		Map<String, Object> response = new HashMap<>();

		if (bu.getStatusCodeValue() == 404 || bu.getStatusCodeValue() == 500) {
			return bu;
		}

		try {
			uRepository.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al eliminar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Usuario <" + id + "> eliminado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/*
	 * Modifica registro
	 */
	public ResponseEntity<?> actualizaUsuario(UsuariosModel um, int id) throws Exception {

		Map<String, Object> response = new HashMap<>();
		UsuariosModel um2 = null;

		ResponseEntity<?> uId = buscaUsuario(id);
		if (uId.getStatusCodeValue() == 404 || uId.getStatusCodeValue() == 500) {
			return uId;
		}

		ResponseEntity<?> u = buscaUNombreUsuario(um.getNombreUsuario());
		if (u != null && u.getStatusCodeValue() == 500) {
			return u;
		}
		
		ResponseEntity<?> unID = buscaUNID(id);


		if (u != null)
			um2 = (UsuariosModel) u.getBody();
		else
			um2 = (UsuariosModel) unID.getBody();

		if (um2 != null && (um2.getId() != id)) {
			response.put("mensaje", "Nombre de usuario <" + um2.getNombreUsuario() + "> ya existe.");
			response.put("statusCodeValue", HttpStatus.BAD_REQUEST.value());
			response.put("statusCode", "BAD_REQUEST");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

//		validaCampos(um);
		UsuariosEntity ue = new UsuariosEntity(um, id);

		if (um.getContrasena() == "") {
			ue.setContrasena(um2.getContrasena());
		} else {
			ue.setContrasena(encoder.encode(um.getContrasena()));
		}
		try {
			uRepository.save(ue);

		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "Usuario <" + id + "> modificado correctamente.");
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public Long total(String nombre) throws Exception {
		if (nombre == null)
			nombre = "%";
		return uRepository.count();
	}

	private ResponseEntity<?> buscaUNombreUsuario(String nombre) {

		UsuariosEntity u = null;
		Map<String, Object> response = new HashMap<>();

		try {
			u = uRepository.findByNombreusuario(nombre);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (u != null) {
			UsuariosModel um = new UsuariosModel(u);
			return ResponseEntity.ok(um);
		} else {
			return null;
		}

	}
	
	private ResponseEntity<?> buscaUNID(int id) {

		UsuariosEntity u = null;
		Map<String, Object> response = new HashMap<>();

		try {
			u = uRepository.findById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al modificar recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (u != null) {
			UsuariosModel um = new UsuariosModel(u);
			return ResponseEntity.ok(um);
		} else {
			return null;
		}

	}

	/*
	 * Busca registro por id
	 */
	private ResponseEntity<?> buscaUsuario(int id) {

		Map<String, Object> response = new HashMap<>();
		UsuariosEntity ue = null;

		try {
			ue = uRepository.findById(id);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (ue == null) {
			response.put("mensaje", "El usuario no se encuentra con ID <" + id + ">");
			response.put("statusCodeValue", HttpStatus.NOT_FOUND.value());
			response.put("statusCode", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}

		UsuariosModel um = new UsuariosModel(ue);

		response.put("body", um);
		response.put("statusCodeValue", HttpStatus.OK.value());
		response.put("statusCode", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	/*
	 * Busca registro por nombre Usuario
	 */
	private ResponseEntity<?> buscaNombreUsuario(String nombreUsuario) {

		Map<String, Object> response = new HashMap<>();
		UsuariosEntity u = null;

		try {
			u = uRepository.findByNombreusuario(nombreUsuario);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al obtener recurso de la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (u != null) {
			response.put("mensaje", "El nombre de usuario <" + nombreUsuario + "> ya existe.");
			response.put("statusCodeValue", HttpStatus.BAD_REQUEST.value());
			response.put("statusCode", "BAD_REQUEST");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		return null;
	}

	/*
	 * Validamos campos de Nombre usuario, nombre Completo, contraseña y Email
	 */
	private void validaCampos(UsuariosModel um) {

		if (um.getNombreUsuario() == null || um.getNombreUsuario().isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay nombre de usuario");
		/*
		 * if (um.getContrasena() == null || um.getContrasena().isEmpty()) throw new
		 * ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay contraseña");
		 */
		if (um.getNombre() == null || um.getNombre().isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay nombre");

		if (um.getEmail() == null || um.getEmail().isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay Email");

	}

}
