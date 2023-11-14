import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { CadastrarUsuarioComponent } from './cadastrar-usuario.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, Observable } from 'rxjs';
import { UsuarioService } from 'src/app/services/service-usuario.service';
import { UsuarioResponse } from 'src/app/entidades/usuarioResponse';
import { Router } from '@angular/router';
import { Usuario } from 'src/app/entidades/usuario';

const usuarioServiceMock = jasmine.createSpyObj('UsuarioService', ['cadastrar']);
const response = {
  id: 1,
  nome: 'teste',
  email: 'teste@teste',
};

class UsuarioServiceMock extends UsuarioService {
  override cadastrar(usuario: Usuario): Observable<UsuarioResponse> {
    // Mock uma resposta bem-sucedida aqui
    return of(response);
  }
}

describe('CadastrarUsuarioComponent', () => {
  let component: CadastrarUsuarioComponent;
  let fixture: ComponentFixture<CadastrarUsuarioComponent>;
  let router: Router;
  let usuarioService: UsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CadastrarUsuarioComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule],
      providers: [
        // Substitua o serviço real pelo mock
        {
          provide: UsuarioService,
          useValue: usuarioServiceMock
        },
      ]
    });

    fixture = TestBed.createComponent(CadastrarUsuarioComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    usuarioService = TestBed.inject(UsuarioService);
    // Obtenha uma instância do serviço mock
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve apresentar mensagem de erro quando for digitado um e-mail inválido', fakeAsync(() => {
    const emailInput = fixture.debugElement.query(
      By.css('input#email')
    ).nativeElement;
    emailInput.value = 'emailinvalido';
    emailInput.dispatchEvent(new Event('input'));

    fixture.detectChanges();
    tick();

    const errorMessageElement = fixture.debugElement.query(
      By.css('.mensagem__erro')
    );
    expect(errorMessageElement).toBeTruthy();
    expect(errorMessageElement.nativeElement.textContent).toContain(
      'Informe um e-mail válido.'
    );
  }));

  it('should disable submit button when form is invalid', fakeAsync(() => {
    // Simulate entering valid data in the form
    component.formulario.patchValue({
      nome: 'John Doe',
      email: 'john@example.com',
      senha: 'password',
      confirmacaoDeSenha: 'password',
    });

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify that the submit button is enabled when the form is valid
    const submitButton = fixture.debugElement.query(
      By.css('button[type="submit"]')
    ).nativeElement;
    expect(submitButton.disabled).toBeFalsy();

    // Simulate entering invalid data in the form
    component.formulario.get('senha')?.setValue('pass'); // Assuming minimum length for senha is 6

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify that the submit button is disabled when the form is invalid
    expect(submitButton.disabled).toBeTruthy();
  }));

  it('should show error messages for blank fields', fakeAsync(() => {
    // Simulate form submission with blank fields
    component.cadastrarUsuario();

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify error messages for blank fields
    expect(component.formulario.get('nome')?.hasError('required')).toBeTruthy();
    expect(
      component.formulario.get('email')?.hasError('required')
    ).toBeTruthy();
    expect(
      component.formulario.get('senha')?.hasError('required')
    ).toBeTruthy();
    expect(
      component.formulario.get('confirmacaoDeSenha')?.hasError('required')
    ).toBeTruthy();
  }));

  it('should show error message for minimum password length', fakeAsync(() => {
    // Simulate entering a password with less than 6 characters
    component.formulario.get('senha')?.setValue('pass');

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify error message for minimum password length
    expect(component.formulario.get('senha')?.hasError('minlength')).toBeTruthy();
  }));

  it('should show error message for maximum password length', fakeAsync(() => {
    // Simulate entering a password with more than 20 characters
    component.formulario.get('senha')?.setValue('a'.repeat(21));

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify error message for maximum password length
    expect(component.formulario.get('senha')?.hasError('maxlength')).toBeTruthy();
  }));

  it('should show error message for password containing spaces', fakeAsync(() => {
    // Simulate entering a password with spaces
    component.formulario.get('senha')?.setValue('          ');

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify error message for password containing spaces
    expect(component.formulario.get('senha')?.hasError('pattern')).toBeTruthy();
  }));

  it('should clear form fields when "Limpar" button is clicked', fakeAsync(() => {
    // Simulate entering data in the form
    component.formulario.patchValue({
      nome: 'John Doe',
      email: 'john@example.com',
      senha: 'password',
      confirmacaoDeSenha: 'password',
    });

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify that the form fields are filled
    expect(component.formulario.get('nome')?.value).toBe('John Doe');
    expect(component.formulario.get('email')?.value).toBe('john@example.com');
    expect(component.formulario.get('senha')?.value).toBe('password');
    expect(component.formulario.get('confirmacaoDeSenha')?.value).toBe('password');

    // Simulate clicking the "Limpar" button
    const limparButton = fixture.debugElement.query(By.css('.botao-cancelar')).nativeElement;
    limparButton.click();

    // Trigger change detection
    fixture.detectChanges();
    tick();

    // Verify that the form fields are cleared
    expect(component.formulario.get('nome')?.value).toBe('');
    expect(component.formulario.get('email')?.value).toBe('');
    expect(component.formulario.get('senha')?.value).toBe('');
    expect(component.formulario.get('confirmacaoDeSenha')?.value).toBe('');
  }));

  it('should call cadastrarUsuario() when "Salvar" button is clicked and form is valid', () => {
    spyOn(component, 'cadastrarUsuario'); // Certifique-se de usar spyOn diretamente no componente

    const salvarButton = fixture.debugElement.query(By.css('#botao-salvar'));
    expect(salvarButton).toBeTruthy();

    salvarButton.triggerEventHandler('click', null);

    expect(component.cadastrarUsuario).toHaveBeenCalled();
  });


});
