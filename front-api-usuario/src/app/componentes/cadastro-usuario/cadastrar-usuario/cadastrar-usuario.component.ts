import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/service-usuario.service';
import { confirmaSeSenhasCoincidem } from 'src/app/validacoes/confirmaSeSenhasCoincidem';

@Component({
  selector: 'app-cadastrar-usuario',
  templateUrl: './cadastrar-usuario.component.html',
  styleUrls: ['./cadastrar-usuario.component.css']
})
export class CadastrarUsuarioComponent implements OnInit {

  public formulario!: FormGroup;

  constructor(private service: UsuarioService, private router: Router, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      nome: ['', Validators.compose([
        Validators.required,
        Validators.pattern(/(.|\s)*\S(.|\s)*/),//não permite espaços vazios
        Validators.minLength(3),
        Validators.maxLength(50)
      ])],
      email: ['', Validators.compose([
        Validators.required,
        Validators.email
      ])],
      senha: ['', Validators.compose([
        Validators.required,
        Validators.pattern(/(.|\s)*\S(.|\s)*/),//não permite espaços vazios
        Validators.minLength(6),
        Validators.maxLength(20)
      ])],
      confirmacaoDeSenha: ['', Validators.compose([
        Validators.required,
        confirmaSeSenhasCoincidem
      ])]
    })
  }

  cadastrarUsuario() {
    if(this.formulario.valid){
      this.service.cadastrar(this.formulario.value).subscribe(() => {
      this.router.navigate(['/cadastroSucesso']);
      });
    }

  }

  cancelarCadastro(){
    this.formulario.get('nome')?.setValue('');
    this.formulario.get('email')?.setValue('');
    this.formulario.get('senha')?.setValue('');
    this.formulario.get('confirmacaoDeSenha')?.setValue('');
    this.router.navigate(['/']);
  }

  habilitarBotao(): string{
    if(this.formulario.valid){
      return 'botao-salvar';
    }else{
      return 'botao__desabilitado';
    }
  }

}


