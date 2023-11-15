import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { CadastrarUsuarioComponent } from './componentes/cadastro-usuario/cadastrar-usuario/cadastrar-usuario.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CadastroSucessoComponent } from './componentes/cadastro-usuario/cadastro-sucesso/cadastro-sucesso.component';
import { RodapeComponent } from './componentes/rodape/rodape.component';
import { CabecalhoComponent } from './componentes/cabecalho/cabecalho.component';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';


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
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
