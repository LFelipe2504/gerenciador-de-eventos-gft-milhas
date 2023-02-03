package com.gft.gerenciador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.gerenciador.entities.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{

}
