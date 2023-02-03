package com.gft.gerenciador.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gft.gerenciador.entities.Atividade;
import com.gft.gerenciador.entities.Evento;
import com.gft.gerenciador.entities.Grupo;
import com.gft.gerenciador.entities.Participante;
import com.gft.gerenciador.entities.Usuario;
import com.gft.gerenciador.services.AtividadeService;
import com.gft.gerenciador.services.EventoService;
import com.gft.gerenciador.services.GrupoService;
import com.gft.gerenciador.services.ParticipanteService;
import com.gft.gerenciador.services.UsuarioService;

@Controller
@RequestMapping("/popularbdd")
public class PopularBDDController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParticipanteService participanteService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private AtividadeService atividadeService;
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping
	public String popularBDD() throws ParseException {
		
		Usuario usuario = new Usuario("Michel Menezes", "michel.menezes@gft.com", "mize", "123456");
		usuarioService.salvarUsuario(usuario);
		
		Participante participante = new Participante("Lucas Martins", "lucas.martins@gft.com", "luti","L1" );
		Participante participante2 = new Participante("Marcos Lima", "marcos.lima@gft.com", "coli","L2" );
		participanteService.salvarParticipante(participante);
		participanteService.salvarParticipante(participante2);
		
		Grupo grupo = new Grupo("Alfa");
		Grupo grupo2 = new Grupo("Delta");
		grupoService.salvarGrupo(grupo);
		grupoService.salvarGrupo(grupo2);
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		
		String dataInicialAtividade = "30/10/2022";		
		String dataFinalAtividade = "15/11/2022";		
		Atividade atividade = new Atividade("Fazer CRUD aplicação",formato.parse(dataInicialAtividade), formato.parse(dataFinalAtividade));
		
		String dataInicialAtividade2 = "05/11/2022";		
		String dataFinalAtividade2 = "10/11/2022";		
		Atividade atividade2 = new Atividade("Implementar autenticação",formato.parse(dataInicialAtividade2), formato.parse(dataFinalAtividade2));
		atividadeService.salvarAtividade(atividade);
		atividadeService.salvarAtividade(atividade2);
		
		String dataInicialEvento = "29/10/2022";		
		String dataFinalEvento = "16/11/2022";
		Evento evento = new Evento("Sistema Bancário", formato.parse(dataInicialEvento), formato.parse(dataFinalEvento));
		
		String dataInicialEvento2 = "04/11/2022";		
		String dataFinalEvento2 = "11/11/2022";
		Evento evento2 = new Evento("Controle Academia", formato.parse(dataInicialEvento2), formato.parse(dataFinalEvento2));
		eventoService.salvarEvento(evento);
		eventoService.salvarEvento(evento2);
		
		participante.setGrupo(grupo);		
		participante2.setGrupo(grupo2);		
		participanteService.salvarParticipante(participante);
		participanteService.salvarParticipante(participante2);
		
		grupo.setEvento(evento);
		grupo2.setEvento(evento2);		
		grupoService.salvarGrupo(grupo);
		grupoService.salvarGrupo(grupo2);
		
		atividade.setEvento(evento);
		atividade2.setEvento(evento2);
		atividadeService.salvarAtividade(atividade);
		atividadeService.salvarAtividade(atividade2);
		
		eventoService.salvarEvento(evento);
		eventoService.salvarEvento(evento2);		
		
		return "/home";
	}
}
