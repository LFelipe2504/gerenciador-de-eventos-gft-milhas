package com.gft.gerenciador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.gerenciador.entities.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long>{
	
	List<Participante> findByNomeContains(String nome);

}
