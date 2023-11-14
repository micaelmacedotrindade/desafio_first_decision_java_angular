import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import { Usuario } from '../entidades/usuario';
import { Observable } from 'rxjs';
import { UsuarioResponse } from '../entidades/usuarioResponse';
import { API_PATH } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { //injeção de delpendências via construtor
  }

  cadastrar(usuario: Usuario): Observable<UsuarioResponse>{
    return this.http.post<UsuarioResponse>(`${API_PATH}`, usuario)
  }

}
