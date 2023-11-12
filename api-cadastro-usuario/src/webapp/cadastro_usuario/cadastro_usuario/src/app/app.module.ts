import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CabecalhoComponent } from './componentes/cabecalho/cabecalho.component';
import { RodapeComponent } from './componentes/rodape/rodape.component';
import { CadastrarUsuarioComponent } from './componentes/cadastro-usuario/cadastrar-usuario/cadastrar-usuario.component';
import { FormsModule } from '@angular/forms';
import { CadastroSucessoComponent } from './componentes/cadastro-usuario/cadastro-sucesso/cadastro-sucesso.component';

@NgModule({
  declarations: [
    AppComponent,
    CabecalhoComponent,
    RodapeComponent,
    CadastrarUsuarioComponent,
    CadastroSucessoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
