package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.infraestrutura.security.DadosTokenJWT;
import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.infraestrutura.security.TokenService;
import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuariosistema.DadosAutenticacaoRecord;
import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuariosistema.UsuarioSistema;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<DadosTokenJWT> efetuarLogin (@RequestBody  @Valid DadosAutenticacaoRecord dados) {
		var autenticacaoDoToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autenticacao = manager.authenticate(autenticacaoDoToken);
		var tokenJWT = tokenService.gerarToken((UsuarioSistema) autenticacao.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}

}