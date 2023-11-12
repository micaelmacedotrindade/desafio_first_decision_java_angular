import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastrarUsuarioComponent } from './componentes/cadastro-usuario/cadastrar-usuario/cadastrar-usuario.component';
import { CadastroSucessoComponent } from './componentes/cadastro-usuario/cadastro-sucesso/cadastro-sucesso.component';

const routes: Routes = [
  {
     path: 'cadastrarUsuario',
     component: CadastrarUsuarioComponent
  },
  {
    path: 'cadastroSucesso',
    component: CadastroSucessoComponent
  },
  {
    path: '',
    redirectTo: 'cadastrarUsuario',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
