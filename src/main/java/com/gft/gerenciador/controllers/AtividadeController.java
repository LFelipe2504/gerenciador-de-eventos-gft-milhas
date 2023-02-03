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

import com.gft.gerenciador.entities.Atividade;
import com.gft.gerenciador.services.AtividadeService;
import com.gft.gerenciador.services.EventoService;

@Controller
@RequestMapping("/atividade")
public class AtividadeController {
	
	@Autowired
	private AtividadeService service;
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping("/editar")
	public ModelAndView editarAtividade(@RequestParam(required = false) Long id){
		ModelAndView mv = new ModelAndView("atividade/form.html");
		
		Atividade atividade;
		
		if(id == null) {		
			atividade = new Atividade();
		}
		else {
			try {
				atividade = service.obterAtividade(id);
			} catch (Exception e) {
				atividade = new Atividade();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("atividade", atividade);
		mv.addObject("listaEventos", eventoService.listarEventos());
		
		return mv;
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarAtividade(@Valid Atividade atividade, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("atividade/form.html");
		
		boolean novo = true;
		
		if(atividade.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {		
			mv.addObject("atividade", atividade);
			return mv;
		}
		
		if( atividade.getDataEntrega().compareTo(atividade.getDataInicio()) >= 0) {			
			service.salvarAtividade(atividade);
		}else {
			mv.addObject("atividade", atividade);
			mv.addObject("mensagem", "Erro ao inserir as datas de início e finalização da atividade.");
			return mv;
		}
		
		service.salvarAtividade(atividade);
		
		if(novo)
			mv.addObject("atividade", new Atividade());
		else
			mv.addObject("atividade", atividade);
			
		
		mv.addObject("mensagem", "Atividade salva com sucesso.");
		mv.addObject("listaEventos", eventoService.listarEventos());
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView listarAtividades(String nome) {
		ModelAndView mv = new ModelAndView("atividade/listar.html");
		mv.addObject("lista", service.listarAtividades(nome));

		return mv;
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirAtividade(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/atividade");
		try {
			service.excluirAtividade(id);
			redirectAttributes.addFlashAttribute("mensagem", "Atividade excluída com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir atividade! " + e.getMessage());
		}

		return mv;
	}

}
