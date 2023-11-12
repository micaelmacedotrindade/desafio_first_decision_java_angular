package com.desafio.api.cadastro.infraestrutura.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.api.cadastro.domain.data.usuariosistema.UsuarioSistemaRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //classe generica , apenas para o spring carregar
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioSistemaRepository usuarioSistemaRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		
		var tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			var subject = tokenService.getSubject(tokenJWT);
			var usuarioSistema = usuarioSistemaRepository.findByLogin(subject);
			var authentication = new UsernamePasswordAuthenticationToken(usuarioSistema, usuarioSistema.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication );
		}
		
		//chama o proximo filtro e segue o fluxo
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader != null) {
			return authHeader.replace("Beare", "");
		}
		return null;
	}
}
