package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuariosistema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
	public UserDetails findByLogin(String login);
}
