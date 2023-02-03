package com.gft.gerenciador.controllers;

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

import com.gft.gerenciador.entities.Participante;
import com.gft.gerenciador.services.GrupoService;
import com.gft.gerenciador.services.ParticipanteService;

@Controller
@RequestMapping("/participante")
public class ParticipanteController {
	
	@Autowired
	private ParticipanteService service;
	
	@Autowired
	private GrupoService grupoService;
	
	@GetMapping("/editar")
	public ModelAndView editarParticipante(@RequestParam(required = false) Long id){
		ModelAndView mv = new ModelAndView("participante/form.html");
		
		Participante participante;
		
		if(id == null) {		
			participante = new Participante();
		}
		else {
			try {
				participante = service.obterParticipante(id);
			} catch (Exception e) {
				participante = new Participante();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("participante", participante);
		mv.addObject("listaGrupos", grupoService.listarGrupos(null));
		
		return mv;
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarParticipante(@Valid Participante participante, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("participante/form.html");
		
		boolean novo = true;
		
		if(participante.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {		
			mv.addObject("participante", participante);
			return mv;
		}	
		
		service.salvarParticipante(participante);
		
		if(novo)
			mv.addObject("participante", new Participante());
		else
			mv.addObject("participante", participante);
			
		
		mv.addObject("mensagem", "Participante salvo com sucesso.");
		mv.addObject("listaGrupos", grupoService.listarGrupos(null));
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView listarParticipantes( String nome) {
		ModelAndView mv = new ModelAndView("participante/listar.html");
		mv.addObject("lista", service.listarParticipantes(nome));

		return mv;
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirParticipante(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/participante");
		try {
			service.excluirParticipante(id);
			redirectAttributes.addFlashAttribute("mensagem", "Participante excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir Participante! " + e.getMessage());
		}

		return mv;
	}

}
