package com.gft.gerenciador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.gerenciador.entities.Atividade;
import com.gft.gerenciador.repositories.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository repository;

	public Atividade obterAtividade(Long id) throws Exception {

		Optional<Atividade> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Atividade não encontrada.");

		return optional.get();
	}

	public void salvarAtividade(Atividade atividade) {
		repository.save(atividade);
	}

	public List<Atividade> listarAtividades(String nome) {
		if (nome != null)
			return repository.findByNomeContains(nome);

		return repository.findAll();
	}

	public void excluirAtividade(Long id) throws Exception {
		
		repository.deleteById(id);
	}	

}
