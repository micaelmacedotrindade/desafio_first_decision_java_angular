import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ServiceUsuarioService {

  API

  constructor(private http: HttpClient) { //injeção de delpendências via construtor

  }
}
