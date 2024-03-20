package com.lace.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lace.entity.EstudiosEntity;

@Repository("estudiosRepository")
public interface EstudiosRepository extends JpaRepository<EstudiosEntity, Serializable>{

	@Query(value = "SELECT * FROM estudios2 WHERE nombre LIKE %:nombre%", nativeQuery = true)
	public abstract Page<EstudiosEntity> findAll(Pageable pageable, @Param("nombre") String nombre);
	
	public abstract EstudiosEntity findById(int id);
	
	public abstract EstudiosEntity findByNombre(String nombre);
	
	@Query(value = "SELECT * FROM estudios2 WHERE nombre LIKE %:nombre%", nativeQuery = true)
	public abstract List<EstudiosEntity> findByNombrelike(@Param("nombre") String nombre);
	
	@Query(value = "SELECT COUNT(id) FROM estudios2 WHERE nombre LIKE %:nombre%", nativeQuery = true)
	public abstract Long count(@Param("nombre") String nombre);
}
