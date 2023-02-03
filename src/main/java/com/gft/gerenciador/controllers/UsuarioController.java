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

import com.gft.gerenciador.entities.Usuario;
import com.gft.gerenciador.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/editar")
	public ModelAndView editarUsuario(@RequestParam(required = false) Long id){
		ModelAndView mv = new ModelAndView("usuario/form.html");
		
		Usuario usuario;
		
		if(id == null) {		
			usuario = new Usuario();
		}
		else {
			try {
				usuario = service.obterUsuario(id);
			} catch (Exception e) {
				usuario = new Usuario();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("usuario", usuario);
		
		return mv;
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarUsuario(@Valid Usuario usuario, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("usuario/form.html");
		
		boolean novo = true;
		
		if(usuario.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {		
			mv.addObject("usuario", usuario);
			return mv;
		}	
		
		service.salvarUsuario(usuario);
		
		if(novo)
			mv.addObject("usuario", new Usuario());
		else
			mv.addObject("usuario", usuario);
			
		
		mv.addObject("mensagem", "Usuário salvo com sucesso.");
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView listarUsuarios( String nome) {
		ModelAndView mv = new ModelAndView("usuario/listar.html");
		mv.addObject("lista", service.listarUsuarios( nome));

		return mv;
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirUsuario(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/usuario");
		try {
			service.excluirUsuario(id);
			redirectAttributes.addFlashAttribute("mensagem", "Desenvolvedor excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir Desenvolvedor! " + e.getMessage());
		}

		return mv;
	}

}
