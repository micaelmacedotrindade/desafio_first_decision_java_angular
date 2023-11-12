export class Usuario {

  constructor(private id: number, private nome: string, private email: string, private senha: string, private confirmacaoDeSenha: string){
  }

  public getId(): number{
    return this.id;
  }

  public getNome(): string{
    return this.nome;
  }

  public getEmail(): string{
    return this.email;
  }

  public getSenha(): string{
    return this.senha;
  }

  public getConfirmacaoDeSenha(): string{
    return this.confirmacaoDeSenha;
  }

  public setId(id: number){
   this.id = id;
  }

  public setNome(nome: string){
   this.nome = nome;
  }

  public setEmail(email: string){
   this.email = email;
  }

  public setSenha(senha: string){
   this.senha = senha;
  }

  public setConfirmacaoDeSenha(confirmacaoDeSenha: string){
   this.confirmacaoDeSenha = confirmacaoDeSenha;
  }

}
