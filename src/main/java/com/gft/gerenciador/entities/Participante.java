package com.gft.gerenciador.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Participante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	private String nome;	
	
	@Email(message = "Email inválido.")
	private String email;
	
	@NotEmpty(message = "Quatro letras não pode ser vazio.")
	@Size(max = 4,min = 4, message = "Deve ser quatro letras.")
	private String quatroLetras;
	
	@ManyToOne
	private Grupo grupo;
	
	private String nivel;	
	
	public Participante() {
	}	
	
	public Participante(@NotEmpty(message = "Nome não pode ser vazio.") String nome,
			@Email(message = "Email inválido.") String email,
			@NotEmpty(message = "Quatro letras não pode ser vazio.") @Size(max = 4, min = 4, message = "Deve ser quatro letras.") String quatroLetras,
			String nivel) {
		this.nome = nome;
		this.email = email;
		this.quatroLetras = quatroLetras;
		this.nivel = nivel;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQuatroLetras() {
		return quatroLetras;
	}
	public void setQuatroLetras(String quatroLetras) {
		this.quatroLetras = quatroLetras;
	}
	
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
}
