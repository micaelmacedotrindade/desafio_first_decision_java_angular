import { AbstractControl } from '@angular/forms';

export function confirmaSeSenhasCoincidem(campo: AbstractControl) {
  const senha = campo.parent?.get('senha')?.value;
  const confirmacaoDeSenha = campo.parent?.get('confirmacaoDeSenha')?.value;
  if (senha === confirmacaoDeSenha) {
    return null;
  } else {
    return { senhasCoincidem: true };
  }
}
