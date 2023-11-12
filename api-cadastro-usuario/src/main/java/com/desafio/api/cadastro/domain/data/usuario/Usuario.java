package com.desafio.api.cadastro.domain.data.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//TODO: VALIDAÇÃO DE TAMANHO 3 - 50 CARACTERES
	@NotBlank
    private String nome;
    
    @NotBlank
    @Email
    private String email;

	//TODO: VALIDAÇÃO DE TAMANHO 6 - 20 CARACTERES
    private String senha;
    
    @Transient
    private String validacaoSenha;
   
}
