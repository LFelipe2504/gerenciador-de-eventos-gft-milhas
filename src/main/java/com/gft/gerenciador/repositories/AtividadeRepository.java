package com.gft.gerenciador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.gerenciador.entities.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{

	List<Atividade> findByNomeContains(String nome);	

}
