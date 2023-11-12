package com.desafio.api.cadastro.domain.data.usuario;

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
public class UsuarioDTO {
	
	private Long id;

	//TODO: VALIDAÇÃO DE TAMANHO 3 - 50 CARACTERES
	@NotBlank
    private String nome;
    
    @NotBlank
    @Email
    private String email;

	//TODO: VALIDAÇÃO DE TAMANHO 6 - 20 CARACTERES
    private String senha;
    
    private String validacaoSenha;
   
}

