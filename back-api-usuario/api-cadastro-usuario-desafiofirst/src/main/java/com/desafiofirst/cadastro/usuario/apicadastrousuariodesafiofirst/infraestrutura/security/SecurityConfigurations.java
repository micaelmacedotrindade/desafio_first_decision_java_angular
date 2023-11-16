package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.infraestrutura.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	private SecurityFilter securityFilter;
	

	//configurar para ser stateless e permitir que as urls sejam publicas
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
	            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authorizeHttpRequests(req -> {
	                req.requestMatchers(HttpMethod.POST, "/login").permitAll();
	                req.anyRequest().authenticated();
	            })
	            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //adicionando o filtro proprio antes do filtro do spring
	            .build();
	}
	
//	filtrando por perfil
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    return http.csrf().disable()
//	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        .and().authorizeHttpRequests()
//	        .requestMatchers(HttpMethod.POST, "/login").permitAll()
//	        .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
//	        .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
//	        .anyRequest().authenticated()
//	        .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//	        .build();
//	}
	
	
//	@EnableMethodSecurity(securedEnabled = true) controle de acesso por anotações
//	@GetMapping("/{id}")
//	@Secured("ROLE_ADMIN")
//	public ResponseEntity detalhar(@PathVariable Long id) {
//	    var medico = repository.getReferenceById(id);
//	    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
//	}
	
	@Bean
	public AuthenticationManager passWordEncoder(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	//Neste curso, usaremos o algoritmo BCrypt.
	@Bean
	public PasswordEncoder authenticationManager() {
		return new BCryptPasswordEncoder();
	}

}
