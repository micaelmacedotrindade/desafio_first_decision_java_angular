import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Usuario } from 'src/app/entidades/usuario';

@Component({
  selector: 'app-cadastrar-usuario',
  templateUrl: './cadastrar-usuario.component.html',
  styleUrls: ['./cadastrar-usuario.component.css']
})
export class CadastrarUsuarioComponent implements OnInit {

  usuario: Usuario = new Usuario(0,'','','','');

  constructor() { }

  ngOnInit(): void {
  }

  submitForm() {
    // Lógica para enviar dados do usuário (por exemplo, para um serviço de registro).
    console.log('Dados do usuário:', this.usuario);
  }

  cadastrarUsuario() {
    alert("Usuario cadastrado");
  }

  cancelarCadastro(){
    this.usuario = new Usuario(0,'','','','');
    alert("Cadastro cancelado");
  }

}

