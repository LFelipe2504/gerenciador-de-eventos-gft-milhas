package com.gft.gerenciador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.gerenciador.entities.Atividade;
import com.gft.gerenciador.entities.Evento;
import com.gft.gerenciador.entities.Grupo;
import com.gft.gerenciador.repositories.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository repository;

	public Evento obterEvento(Long id) throws Exception {

		Optional<Evento> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Evento não encontrado.");

		return optional.get();
	}

	public void salvarEvento(Evento evento) {		
		repository.save(evento);
	}

	public List<Evento> listarEventos() {
		return repository.findAll();
	}

	public void excluirEvento(Long id) throws Exception {
		Optional<Evento> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Evento não encontrado ou já excluído.");

		Evento evento = optional.get();
		
		repository.delete(evento);

	}

	public List<Grupo> listarGruposDoEvento(Long id) throws Exception {
		Optional<Evento> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Evento não encontrado.");
		
		return optional.get().getListaGrupos();
	}

	public List<Atividade> listarAtividadesDoEvento(Long id) throws Exception {
		Optional<Evento> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Evento não encontrado.");
		
		return optional.get().getListaAtividades();
	}

}
