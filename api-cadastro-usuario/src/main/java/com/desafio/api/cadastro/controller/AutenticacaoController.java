package com.desafio.api.cadastro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api.cadastro.domain.data.usuariosistema.DadosAutenticacaoRecord;
import com.desafio.api.cadastro.domain.data.usuariosistema.UsuarioSistema;
import com.desafio.api.cadastro.infraestrutura.security.DadosTokenJWT;
import com.desafio.api.cadastro.infraestrutura.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/autenticacao")
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
