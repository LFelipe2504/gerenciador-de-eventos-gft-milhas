package com.gft.gerenciador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gft.gerenciador.entities.Usuario;
import com.gft.gerenciador.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public Usuario obterUsuario(Long id) throws Exception {

		Optional<Usuario> optional = repository.findById(id);

		if (optional.isEmpty())
			throw new Exception("Usuário não encontrado.");

		return optional.get();
	}

	public void salvarUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncoder().encode(usuario.getSenha()));
		repository.save(usuario);
	}

	public List<Usuario> listarUsuarios(String nome) {
		if (nome != null)
			return repository.findByNomeContains(nome);

		return repository.findAll();
	}

	public void excluirUsuario(Long id) throws Exception {		
		repository.deleteById(id);
	}

}
