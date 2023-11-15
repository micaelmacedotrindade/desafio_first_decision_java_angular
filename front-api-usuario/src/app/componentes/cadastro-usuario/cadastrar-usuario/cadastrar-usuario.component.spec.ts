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
import { UsuarioService } from 'src/app/services/service-usuario.service';
import { Router } from '@angular/router';

const usuarioServiceMock = jasmine.createSpyObj('UsuarioService', ['cadastrar']);

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

  it('Deve apresentar mensagem de erro ao submeter um formulario inválido', fakeAsync(() => {
    // Simulando um formulario válido
    component.formulario.patchValue({
      nome: 'Teste',
      email: 'teste@teste',
      senha: '123456',
      confirmacaoDeSenha: '123456',
    });

    fixture.detectChanges();
    tick();

    // Verificando se o botão salvar está ativo
    const submitButton = fixture.debugElement.query(
      By.css('button[type="submit"]')
    ).nativeElement;
    expect(submitButton.disabled).toBeFalsy();

    // Simulando inserir um dado inválido no formulario
    component.formulario.get('senha')?.setValue('pass'); // senha com menos de 6 caracteres

    fixture.detectChanges();
    tick();

    // Verificando se o botão salvar foi desabilitado
    expect(submitButton.disabled).toBeTruthy();
  }));

  it('Deve apresentar mensagem de erro quando o campo estiver em branco', fakeAsync(() => {
    // Simulando formulário em branco
    component.cadastrarUsuario();

    fixture.detectChanges();
    tick();

    // Verificando erro de obrigatoriedade de preenchimento dos campos
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

  it('Deve apresentar mensagem de ero quando o campo senha estiver com menos de 6 caracteres', fakeAsync(() => {
    // Simulando inserir senha com menos de 6 caracteres
    component.formulario.get('senha')?.setValue('pass');

    fixture.detectChanges();
    tick();

    // Verificando erro de senha inválida
    expect(component.formulario.get('senha')?.hasError('minlength')).toBeTruthy();
  }));

  it('Deve apresentar mensagem de ero quando o campo senha estiver com mais de 20 caracteresh', fakeAsync(() => {
    // Simulando inserir senha com mais de 20 caracteres
    component.formulario.get('senha')?.setValue('a'.repeat(21));

    fixture.detectChanges();
    tick();

    // Verificando erro de senha inválida
    expect(component.formulario.get('senha')?.hasError('maxlength')).toBeTruthy();
  }));

  it('Deve apresentar erro ao inserir senha apenas com espaços em branco', fakeAsync(() => {
    // Inserindo senha apenas com espaços em branco
    component.formulario.get('senha')?.setValue('          ');

    fixture.detectChanges();
    tick();

    // Verificando erro de senha inválida
    expect(component.formulario.get('senha')?.hasError('pattern')).toBeTruthy();
  }));

  it('Deve limpar todos os campos quando o botão Limpar for clicado', fakeAsync(() => {
    // Simulate entering data in the form
    component.formulario.patchValue({
      nome: 'teste',
      email: 'teste@teste',
      senha: '123456',
      confirmacaoDeSenha: '123456',
    });

    fixture.detectChanges();
    tick();

    // Verificando se os campos foram preenchidos
    expect(component.formulario.get('nome')?.value).toBe('teste');
    expect(component.formulario.get('email')?.value).toBe('teste@teste');
    expect(component.formulario.get('senha')?.value).toBe('123456');
    expect(component.formulario.get('confirmacaoDeSenha')?.value).toBe('123456');

    // Clicando no botão Limpar
    const limparButton = fixture.debugElement.query(By.css('.botao-cancelar')).nativeElement;
    limparButton.click();

    fixture.detectChanges();
    tick();

    // Verificando se os campos foram limpados
    expect(component.formulario.get('nome')?.value).toBe('');
    expect(component.formulario.get('email')?.value).toBe('');
    expect(component.formulario.get('senha')?.value).toBe('');
    expect(component.formulario.get('confirmacaoDeSenha')?.value).toBe('');
  }));

  it('Deve chamar cadastrarUsuario() quando o botão salvar for acionado com o formulário válido', () => {
    //Criando spy
    spyOn(component, 'cadastrarUsuario');

    //Simulando botão salvar
    const salvarButton = fixture.debugElement.query(By.css('#botao-salvar'));
    expect(salvarButton).toBeTruthy();

    //simulando clique em salvar, considerando que o formulario está válido
    salvarButton.triggerEventHandler('click', null);

    //Verificando se a função salvar foi chamada
    expect(component.cadastrarUsuario).toHaveBeenCalled();
  });


});
