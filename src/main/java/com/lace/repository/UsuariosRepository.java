package com.lace.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lace.entity.UsuariosEntity;

@Repository("usuariosRepository")
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Serializable> {

	public abstract UsuariosEntity findByNombreusuario(String nombre_usuario);
	
	@Query(value = "SELECT * FROM usuarios2 WHERE nombre LIKE %:nombre%", nativeQuery = true)
	public abstract Page<UsuariosEntity> findAll(Pageable pageable, @Param("nombre") String nombre);
	
	public abstract UsuariosEntity findById(int id);
	
	@Query(value = "SELECT COUNT(id) FROM usuarios2 WHERE nombre LIKE %:nombre%", nativeQuery = true)
	public abstract Long count(@Param("nombre") String nombre);
}
