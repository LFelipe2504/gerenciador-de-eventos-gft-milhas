package com.gft.gerenciador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.gerenciador.entities.Grupo;
import com.gft.gerenciador.entities.Participante;
import com.gft.gerenciador.repositories.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository repository;

	public Grupo obterGrupo(Long id) throws Exception {

		Optional<Grupo> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Grupo não encontrado.");

		return optional.get();
	}

	public void salvarGrupo(Grupo grupo) {		
		repository.save(grupo);
	}

	public List<Grupo> listarGrupos(String nome) {
		if (nome != null)
			return repository.findByNomeContains(nome);

		return repository.findAll();
	}

	public void excluirGrupo(Long id) throws Exception {
		Optional<Grupo> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Grupo não encontrado ou já excluído.");

		Grupo grupo = optional.get();
		
		repository.delete(grupo);

	}

	public List<Participante> listarParticipantesDoGrupo(Long id) throws Exception {

		Optional<Grupo> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Grupo não encontrado.");
		
		return optional.get().getListaParticipantes();

	}
}
