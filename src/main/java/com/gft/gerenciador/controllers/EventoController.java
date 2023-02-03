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

import com.gft.gerenciador.entities.Atividade;
import com.gft.gerenciador.entities.Evento;
import com.gft.gerenciador.entities.Grupo;
import com.gft.gerenciador.services.AtividadeService;
import com.gft.gerenciador.services.EventoService;
import com.gft.gerenciador.services.GrupoService;

@Controller
@RequestMapping("/evento")
public class EventoController {
	
	@Autowired
	private EventoService service;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private AtividadeService atividadeService;
	
	@GetMapping("/editar")
	public ModelAndView editarEvento(@RequestParam(required = false) Long id){
		ModelAndView mv = new ModelAndView("evento/form.html");
		
		Evento evento;
		
		if(id == null) {		
			evento = new Evento();
		}
		else {
			try {
				evento = service.obterEvento(id);
			} catch (Exception e) {
				evento = new Evento();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		List<Grupo> listaGrupos = grupoService.listarGrupos(null);
		
		mv.addObject("evento", evento);
		mv.addObject("listaGrupos", listaGrupos);
		mv.addObject("listaAtividades", atividadeService.listarAtividades(null));
		
		return mv;
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarEvento(@Valid Evento evento, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("evento/form.html");
		
		boolean novo = true;
		
		if(evento.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {		
			mv.addObject("evento", evento);
			return mv;
		}	
		
		if( evento.getDataFinal().compareTo(evento.getDataInicio()) >= 0) {			
			service.salvarEvento(evento);
		}else {
			mv.addObject("evento", evento);
			mv.addObject("mensagem", "Erro ao inserir as datas de início e finalização do evento.");
			return mv;
		}	
		
		if(novo)
			mv.addObject("evento", new Evento());
		else
			mv.addObject("evento", evento);
			
		
		mv.addObject("mensagem", "Evento salvo com sucesso.");
		mv.addObject("listaGrupos", grupoService.listarGrupos(null));
		mv.addObject("listaAtividades", atividadeService.listarAtividades(null));
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView listarEventos() {
		ModelAndView mv = new ModelAndView("evento/listar.html");
		mv.addObject("lista", service.listarEventos());

		return mv;
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirEvento(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/evento");
		
		try {
			service.excluirEvento(id);
			redirectAttributes.addFlashAttribute("mensagem", "Evento excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir evento! " + e.getMessage());
		}

		return mv;
	}
	
	@GetMapping("/grupos")
	public ModelAndView listarGruposDoEvento( @RequestParam Long id) {
		ModelAndView mv = new ModelAndView("evento/gruposEvento.html");
		
		List<Grupo> listaGruposEvento = null;
		Evento evento = null;
		
		try {
			listaGruposEvento = service.listarGruposDoEvento(id);
			evento = service.obterEvento(id);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		
		mv.addObject("lista", listaGruposEvento);
		mv.addObject("evento", evento);				 
		mv.addObject("grupo", new Grupo());		

		return mv;
	}
	
	@PostMapping("/grupos")
	public ModelAndView salvarGrupoNoEvento(@Valid Grupo grupo, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		grupo.setId(null);	
		
		grupoService.salvarGrupo(grupo);			
		
		redirectAttributes.addAttribute("id", grupo.getEvento().getId());
		
		ModelAndView mv = new ModelAndView("redirect:/evento/grupos?id={id}");
					
		if(bindingResult.hasErrors()) {		
			mv.addObject("grupo", grupo);
			return mv;
		}			
		
		return mv;
	}
	
	@GetMapping("/atividades")
	public ModelAndView listarAtividadesDoEvento( @RequestParam Long id) {
		ModelAndView mv = new ModelAndView("evento/atividadesEvento.html");
		
		List<Atividade> listaAtividadesEvento = null;
		Evento evento = null;
		
		try {
			listaAtividadesEvento = service.listarAtividadesDoEvento(id);
			evento = service.obterEvento(id);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		
		mv.addObject("lista", listaAtividadesEvento);
		mv.addObject("evento", evento);				 
		mv.addObject("atividade", new Atividade());		

		return mv;
	}
	
	@PostMapping("/atividades")
	public ModelAndView salvarAtividadeNoEvento(@Valid Atividade atividade, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		atividade.setId(null);	
		
		redirectAttributes.addAttribute("id", atividade.getEvento().getId());
		
		ModelAndView mv = new ModelAndView("redirect:/evento/atividades?id={id}");
		
		if( atividade.getDataEntrega().compareTo(atividade.getDataInicio()) >= 0) {			
			atividadeService.salvarAtividade(atividade);
		}else {
			mv.addObject("atividade", atividade);
			mv.addObject("mensagem", "Erro ao inserir as datas de início e finalização da atividade.");
			return mv;
		}
					
		if(bindingResult.hasErrors()) {		
			mv.addObject("atividade", atividade);
			return mv;
		}			
		
		return mv;
	}

}
