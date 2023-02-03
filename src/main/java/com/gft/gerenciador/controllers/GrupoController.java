package com.gft.gerenciador.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.gerenciador.entities.Grupo;
import com.gft.gerenciador.entities.Participante;
import com.gft.gerenciador.services.EventoService;
import com.gft.gerenciador.services.GrupoService;
import com.gft.gerenciador.services.ParticipanteService;

@Controller
@RequestMapping("/grupo")
public class GrupoController {
	
	@Autowired
	private GrupoService service;
	
	@Autowired
	private ParticipanteService participanteService;
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping("/editar")
	public ModelAndView editarGrupo(@RequestParam(required = false) Long id){
		ModelAndView mv = new ModelAndView("grupo/form.html");
		
		Grupo grupo;
		
		if(id == null) {		
			grupo = new Grupo();
		}
		else {
			try {
				grupo = service.obterGrupo(id);
			} catch (Exception e) {
				grupo = new Grupo();
				mv.addObject("mensagem", e.getMessage());
			}
		}
				
		mv.addObject("grupo", grupo);
		mv.addObject("listaEventos", eventoService.listarEventos());
		
		return mv;
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("grupo/form.html");
		
		boolean novo = true;
		
		if(grupo.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {		
			mv.addObject("grupo", grupo);
			return mv;
		}	
		
		service.salvarGrupo(grupo);
		
		if(novo)
			mv.addObject("grupo", new Grupo());
		else
			mv.addObject("grupo", grupo);
			
		
		mv.addObject("mensagem", "Grupo salvo com sucesso.");
		mv.addObject("listaEventos", eventoService.listarEventos());
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView listarGrupos( String nome) {
		ModelAndView mv = new ModelAndView("grupo/listar.html");
		mv.addObject("lista", service.listarGrupos(nome));

		return mv;
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirGrupo(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/grupo");
		try {
			service.excluirGrupo(id);
			redirectAttributes.addFlashAttribute("mensagem", "Grupo excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir grupo! " + e.getMessage());
		}

		return mv;
	}
	
	@GetMapping("/participantes")
	public ModelAndView listarParticipantesDoGrupo( @RequestParam Long id) {
		ModelAndView mv = new ModelAndView("grupo/participantesGrupo.html");
		List<Participante> listarParticipantesDoGrupo = null;
		Grupo grupo = null;
		
		try {
			listarParticipantesDoGrupo = service.listarParticipantesDoGrupo(id);
			grupo = service.obterGrupo(id);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		
		mv.addObject("lista", listarParticipantesDoGrupo);
		mv.addObject("grupo", grupo);				 
		mv.addObject("participante", new Participante());		

		return mv;
	}
	
	@PostMapping("/participantes")
	public ModelAndView salvarParticipanteEventoNoGrupo(@Valid Participante participante, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		participante.setId(null);	
		
		participanteService.salvarParticipante(participante);			
		
		redirectAttributes.addAttribute("id", participante.getGrupo().getId());
		
		ModelAndView mv = new ModelAndView("redirect:/grupo/participantes?id={id}");
					
		if(bindingResult.hasErrors()) {		
			mv.addObject("participante", participante);
			return mv;
		}			
		
		return mv;
	}

}
