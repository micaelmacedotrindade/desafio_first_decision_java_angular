package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;

    private String nome;

    private String email;

}
