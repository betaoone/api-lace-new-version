package com.lace.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lace.entity.PacientesEntity;

@Repository("pacientesRepository")
public interface PacientesRepository extends JpaRepository<PacientesEntity, Serializable> {

	@Query(value = "SELECT * FROM pacientes2 WHERE nombre LIKE %:name%", nativeQuery = true)
	public abstract Page<PacientesEntity> findAll(Pageable pageable, @Param("name") String name);

	public abstract PacientesEntity findById(int id);

	public abstract PacientesEntity findByNombre(String nombre);

	@Query(value = "SELECT COUNT(id) FROM pacientes2 WHERE nombre LIKE %:nombre%", nativeQuery = true)
	public abstract Long count(@Param("nombre") String nombre);

}
