package com.gft.gerenciador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.gerenciador.entities.Participante;
import com.gft.gerenciador.repositories.ParticipanteRepository;

@Service
public class ParticipanteService {

	@Autowired
	private ParticipanteRepository repository;

	public Participante obterParticipante(Long id) throws Exception {

		Optional<Participante> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Participante não encontrado.");

		return optional.get();
	}

	public void salvarParticipante(Participante participante) {
		repository.save(participante);
	}

	public List<Participante> listarParticipantes(String nome) {
		if (nome != null)
			return repository.findByNomeContains(nome);

		return repository.findAll();
	}

	public void excluirParticipante(Long id) throws Exception {
		repository.deleteById(id);

	}

}
