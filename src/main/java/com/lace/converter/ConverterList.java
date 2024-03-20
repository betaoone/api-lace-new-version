package com.lace.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lace.entity.AnalisisEntity;
import com.lace.entity.EstudiosEntity;
import com.lace.entity.MedicosEntity;
import com.lace.entity.PacientesEntity;
import com.lace.entity.UsuariosEntity;
import com.lace.model.AnalisisModel;
import com.lace.model.EstudiosModel;
import com.lace.model.MedicosModel;
import com.lace.model.PacientesModel;
import com.lace.model.UsuariosModel;

@Component("converterList")
public class ConverterList {

	public List<UsuariosModel> listaUsuarios(List<UsuariosEntity> uEntity){
		
		List<UsuariosModel> uModel = new ArrayList<>();
		
		for(UsuariosEntity usuario : uEntity) {
			uModel.add(new UsuariosModel(usuario));
		}
		
		return uModel;
	}
	
	public List<PacientesModel> listaPacientes(List<PacientesEntity> pE) {
		
		List<PacientesModel> uP = new ArrayList<>() ;
		
		for(PacientesEntity paciente : pE) {
			uP.add(new PacientesModel(paciente));
		}
		
		return uP;
	}
	
	public List<MedicosModel> listaMedicos(List<MedicosEntity> me){
		
		List<MedicosModel> mm = new ArrayList<>();
		
		for(MedicosEntity medico : me) {
			mm.add(new MedicosModel(medico));
		}
		
		return mm;
		
	}
	
	public List<EstudiosModel> listaEstudios(List<EstudiosEntity> e){
		
		List<EstudiosModel> em = new ArrayList<>();
		
		for(EstudiosEntity estudio: e) {
			em.add(new EstudiosModel(estudio));
		}
		
		return em;
	}
	
	public List<AnalisisModel> listaAnaisis(List<AnalisisEntity> ae) {
		
		List<AnalisisModel> am = new ArrayList<>();
		
		for(AnalisisEntity a : ae) {
			am.add(new AnalisisModel(a));
		}
		
		return am;
	}
	
}
