package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.config;

import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario.UsuarioDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhasIguaisValidator implements ConstraintValidator<SenhasIguais, UsuarioDTO> {

    @Override
    public void initialize(SenhasIguais constraintAnnotation) {
    }

    @Override
    public boolean isValid(UsuarioDTO usuarioDTO, ConstraintValidatorContext context) {
        String senha = usuarioDTO.getSenha();
        String confirmacaoDeSenha = usuarioDTO.getConfirmacaoDeSenha();

        return senha != null && senha.equals(confirmacaoDeSenha);
    }
}
