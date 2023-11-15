package com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario.Usuario;
import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario.UsuarioDTO;
import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario.UsuarioRepository;
import com.desafiofirst.cadastro.usuario.apicadastrousuariodesafiofirst.usuario.UsuarioResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CadastroUsuarioControllerTest {

	private static final String MENSAGEM_EMAIL_INVALIDO = "O e-mail deve ter um formato válido.";
	private static final String CAMPO_MENSAGEM = "$[0].mensagem";

	@Autowired
	WebTestClient webTestClient;

	private ObjectMapper objectMapper = new ObjectMapper();;

	@InjectMocks
	private CadastroUsuarioController usuarioController;

	@Mock
	private UsuarioRepository usuarioRepository;
	
	private UsuarioDTO usuarioDTO;
	
	@BeforeEach
	public void setUp() {
		usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail("email@valido"); // Email inválido
		usuarioDTO.setNome("teste");
		usuarioDTO.setSenha("123456");
		usuarioDTO.setConfirmacaoDeSenha("123456");
	}

	@Test
	void deveCadastrarUsuarioERetornarUmUsuarioResponseDtoQuandoOMetodoCadastrarUsuarioForChamado() {
		
		Usuario usuarioEntity = new Usuario();
		usuarioEntity.setId(1l);

		UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

		when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEntity);

		ResponseEntity<UsuarioResponseDTO> responseDto = usuarioController.cadastrarUsuario(usuarioDTO,
				UriComponentsBuilder.newInstance());

		assertEquals(HttpStatus.CREATED, responseDto.getStatusCode());
		assertEquals(1l, responseDto.getBody().getId());
	}

	@Test
	void deveRetornarUmaListaDeUsuariosQuandoOMetodoListarForChamado() {
		Pageable pageable = Pageable.unpaged();

		List<Usuario> usuarios = new ArrayList<>();

		Page<Usuario> usuarioPage = new PageImpl<>(usuarios);

		when(usuarioRepository.findAll(pageable)).thenReturn(usuarioPage);

		ResponseEntity<Page<UsuarioResponseDTO>> responseEntity = usuarioController.listaCadastrosDeUsuario(pageable);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
    public void testUsuarioDTOValidation_NomeEmBranco_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setNome(null); // Nome em branco
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("O nome é obrigatório.");
    }

    @Test
    public void testUsuarioDTOValidation_NomeTamanhoInvalidoComMenosDeTresCaracteres_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setNome("aa"); // Tamanho inválido para o nome
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("O nome deve ter entre 3 e 50 caracteres.");
    }
    
    @Test
    public void testUsuarioDTOValidation_NomeTamanhoInvalidoComMaisDeCinquentaCaracteres_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"); // Tamanho inválido para o nome
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("O nome deve ter entre 3 e 50 caracteres.");
    }

    @Test
    public void testUsuarioDTOValidation_SenhaTamanhoInvalidoComMenosDeSeisCaracteres_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setSenha("123"); // Tamanho inválido para a senha
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("A senha deve ter entre 6 e 20 caracteres.");
    }
    
    @Test
    public void testUsuarioDTOValidation_SenhaTamanhoInvalidoCommaisDeVinteCaracteres_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setSenha("1234567891011121314151617181920"); // Tamanho inválido para a senha
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("A senha deve ter entre 6 e 20 caracteres.");
    }
    
    @Test
    public void testUsuarioDTOValidation_SenhaEmBrancoNull_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setSenha(null); // Tamanho inválido para a senha
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("A senha é obrigatória.");
    }
    
    @Test
    public void testUsuarioDTOValidation_ConfirmacaoDeSenhaEmBrancoNull_DeveRetornarErro() throws JsonProcessingException {
        usuarioDTO.setConfirmacaoDeSenha(null); //Confirmação da senha em nula
        this.webTestClient.post()
                .uri("/cadastro/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath(CAMPO_MENSAGEM).isEqualTo("A confirmacao de senha é obrigatória.");
    }
    
	@Test
	public void testUsuarioDTOValidation_EmailInvalido_DeveRetornarErro() throws JsonProcessingException, Exception {
		
		usuarioDTO.setEmail("emailInvalido"); // Email inválido
		
	    this.webTestClient.post()
	      .uri("/cadastro/usuario")
	      .contentType(MediaType.APPLICATION_JSON)
	      .bodyValue(objectMapper.writeValueAsString(usuarioDTO))
	      .exchange()
	      .expectStatus().isBadRequest()
	      .expectBody()
	      .jsonPath(CAMPO_MENSAGEM).isEqualTo(MENSAGEM_EMAIL_INVALIDO);
		
	}
	
//  this.webTestClient.post()
//	.uri(uriBuilder -> uriBuilder.path("/cadastro/usuario"))
//	.exchange().expectStatus().isOk().expectBody().json(expectedResponseContent);

//  String obj = webTestClient.post().uri("/cadastro/usuario")
//          .accept(MediaType.APPLICATION_JSON).toString();
//  System.out.println(obj);

//	mockMvc.perform(post("/cadastro/usuario").content(objectMapper.writeValueAsString(usuarioDTO))
//			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isBadRequest()).andExpect(MockMvcResultMatchers.content().string(MENSAGEM_EMAIL_INVALIDO));
	
//  String jsonRequest = objectMapper.writeValueAsString(usuarioDTO);
//  mockMvc.perform(post("/cadastro/usuario")
//          .content(jsonRequest)
//          .contentType(MediaType.APPLICATION_JSON)
//          .accept(MediaType.APPLICATION_JSON))
//          .andExpect(status().isBadRequest())
//          .andDo(result -> {
//              String jsonResponse = result.getResponse().getContentAsString();
//              System.out.println("Response JSON: " + jsonResponse);
//          });

}
