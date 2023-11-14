import 'zone.js';
import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UsuarioService } from './service-usuario.service';
import { Usuario } from '../entidades/usuario';
import { Observable, of } from 'rxjs';
import { UsuarioResponse } from '../entidades/usuarioResponse';
import { API_PATH } from 'src/environments/environment';

const usuarioResponse = {
  id: 8,
  nome: 'teste',
  email: 'teste@teste',
};

const usuarioRequest = {
  id: 8,
  nome: 'teste',
  email: 'teste@teste',
  senha: '123456',
  confirmacaoDeSenha: '123456',
};

class UsuarioServiceMock extends UsuarioService {
  override cadastrar(usuario: Usuario): Observable<UsuarioResponse> {
    return of(usuarioResponse);
  }
}

describe('UsuarioService', () => {
  let service: UsuarioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsuarioService]
    });

    service = TestBed.inject(UsuarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request to create a user', fakeAsync(() => {

    const mockResponse: UsuarioResponse = {
      id: 1,
      nome: 'John Doe',
      email: 'john@example.com'
    };

    service.cadastrar(usuarioRequest).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`${API_PATH}`);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();
  }));
});
