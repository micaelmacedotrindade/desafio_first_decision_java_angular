package com.desafio.api.cadastro.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafio.api.cadastro.domain.data.usuario.Usuario;
import com.desafio.api.cadastro.domain.data.usuario.UsuarioDTO;
import com.desafio.api.cadastro.domain.data.usuario.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cadastroUsuario")
public class CadastroUsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	ModelMapper modelMapper = new ModelMapper();

	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDTO> cadastrarUsuario (@RequestBody  @Valid UsuarioDTO usuarioRecord, UriComponentsBuilder uriBuilder) {

		Usuario usuarioEntity = modelMapper.map(usuarioRecord, Usuario.class);
		
		Usuario usuarioPersistence = usuarioRepository
				.save(usuarioEntity);
		
		UsuarioDTO usuarioRecordMap = modelMapper.map(usuarioPersistence, UsuarioDTO.class);

		var uri = uriBuilder.path("/cadastrosDeUsuario/{id}").buildAndExpand(usuarioPersistence.getId()).toUri();
		
		return ResponseEntity.created(uri).body(usuarioRecordMap);

	}

	@GetMapping("/listar")
	public ResponseEntity<Page<UsuarioDTO>> listaCadastrosDeUsuario(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable pageable) {
		var page = usuarioRepository.findAll(pageable).map( p -> 
			modelMapper.map(p, UsuarioDTO.class)
		);
		return ResponseEntity.ok(page);
	}
//
//	@PutMapping("/editarClienteJuridico/{id}")
//	@Transactional
//	public ResponseEntity<UsuarioRecord> editarDadosClienteJuridico(@PathVariable(required = true) Long id,
//			@RequestBody @Valid UsuarioRecord clienteJuridicoRecord) {
//		Usuario clienteJuridicoPersistence = usuarioRepository.save(new Usuario(clienteJuridicoRecord));
//		UsuarioRecord clienteJuridicoRecordRetorno = new UsuarioRecord(clienteJuridicoPersistence.getId(), clienteJuridicoPersistence.getStatus(), 
//				clienteJuridicoPersistence.getEmail(), clienteJuridicoPersistence.getNomeFantasia(), clienteJuridicoPersistence.getRazaoSocial(),
//				clienteJuridicoPersistence.getCnpj(),EnderecoRecord.newListEndereco(clienteJuridicoPersistence.getEndereco()), TelefoneRecord.newListTelefone(clienteJuridicoPersistence.getTelefone()));
//		return ResponseEntity.ok(clienteJuridicoRecordRetorno);
//	}
//
//	@DeleteMapping("/ativarDesativarClienteJuridico/{id}")
//	@Transactional
//	public ResponseEntity<UsuarioRecord> ativarDesativarClienteJuridico(
//			@PathVariable(required = true) @RequestBody @Valid Long id) {
//		Optional<Usuario> clienteJuridicoEncontrado = usuarioRepository.findById(id);
//		if (clienteJuridicoEncontrado.isPresent()) {
//			usuarioRepository.save(clienteJuridicoEncontrado.get().ativarDesativar());
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.notFound().build();
//	}
//	
//	@GetMapping("/id")
//	public ResponseEntity<UsuarioRecord> buscarDadosClienteJuridico(@PathVariable(required = true) Long id, UriComponentsBuilder uriBuilder) {
//		Optional<Usuario> clienteJuridicoPersistenceOptinal = usuarioRepository.findById(id);
//			Usuario clienteJuridicoPersistence = clienteJuridicoPersistenceOptinal.get();
//			var uri = uriBuilder.path("/clientesJuridicos/{id}").buildAndExpand(clienteJuridicoPersistence.getId()).toUri();
//
//			UsuarioRecord clienteJuridicoRecordRetorno = new UsuarioRecord(clienteJuridicoPersistence.getId(), clienteJuridicoPersistence.getStatus(), 
//					clienteJuridicoPersistence.getEmail(), clienteJuridicoPersistence.getNomeFantasia(), clienteJuridicoPersistence.getRazaoSocial(),
//					clienteJuridicoPersistence.getCnpj(),EnderecoRecord.newListEndereco(clienteJuridicoPersistence.getEndereco()), TelefoneRecord.newListTelefone(clienteJuridicoPersistence.getTelefone()));
//			return ResponseEntity.created(uri).body(clienteJuridicoRecordRetorno);
//		
//		
//	}

}
