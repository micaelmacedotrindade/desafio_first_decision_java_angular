package com.desafio.api.cadastro.infraestrutura.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.desafio.api.cadastro.domain.data.usuariosistema.UsuarioSistema;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(UsuarioSistema usuario) {
		//System.out.println(secret);
		try {
		    var algoritimo = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("api-cadastro-usuario")
		        .withSubject(usuario.getLogin())
		        //.withClaim("id", usuario.getId())
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritimo);
		} catch (JWTCreationException e){
		    throw new RuntimeException("Erro ao gerar Token", e);
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
	public String getSubject(String tokenJWT) {
        try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.require(algoritmo)
                                .withIssuer("api-cadastro-usuario")
                                .build()
                                .verify(tokenJWT) //verifica se o token é válido
                                .getSubject();
        } catch (JWTVerificationException exception) {
                throw new RuntimeException("Token JWT inválido ou expirado!");
        }
}

}
