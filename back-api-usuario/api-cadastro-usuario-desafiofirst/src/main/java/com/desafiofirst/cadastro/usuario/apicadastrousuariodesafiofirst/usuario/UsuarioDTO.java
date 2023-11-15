package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario;

import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.config.SenhasIguais;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SenhasIguais(message = "As senhas não coincidem.")
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O e-mail deve ter um formato válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String senha;

    @NotBlank(message = "A confirmacao de senha é obrigatória.")
    private String confirmacaoDeSenha;

}
