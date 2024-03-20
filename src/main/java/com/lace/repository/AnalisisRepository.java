package com.lace.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lace.entity.AnalisisEntity;

@Repository("analisisRepository")
public interface AnalisisRepository extends JpaRepository<AnalisisEntity, Serializable> {

	@Query(value = "SELECT * FROM analisis2 where id_pacientes = :id", nativeQuery = true)
	public abstract Page<AnalisisEntity> findAll(Pageable pageable, @Param("id")int id);
	
	@Query(value = "SELECT * FROM analisis2 WHERE id = ?1 AND id_pacientes = ?2", nativeQuery = true)
	public abstract AnalisisEntity findByIdAndId_pacientes(int id, int id_pacientes);
	
	@Query(value = "SELECT COUNT(id) FROM analisis2 WHERE id_pacientes = ?1", nativeQuery = true)
	public abstract Long count(int id);
}
