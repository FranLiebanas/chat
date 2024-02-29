package com.fran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fran.entity.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	
	@Query("SELECT m FROM Mensaje m WHERE m.emisor.id = ?1 AND m.receptor.id = ?2 ORDER BY m.fechaHora DESC")
    List<Mensaje> findByEmisorIdAndReceptorId(String emisorId, String receptorId);

	@Query("SELECT m FROM Mensaje m WHERE m.emisor.nombre = ?1 AND m.receptor.nombre = ?2 ORDER BY m.fechaHora DESC")
	List<Mensaje> findByEmisorAndReceptorNombre(String emisorNombre, String receptorNombre);


	@Query("SELECT m FROM Mensaje m WHERE m.emisor.nombre = ?1 AND m.receptor.nombre = ?2 ORDER BY m.fechaHora ASC")
	List<Mensaje> findByEmisorNombreAndReceptorNombre(String emisorNombre, String receptorNombre);


	
}