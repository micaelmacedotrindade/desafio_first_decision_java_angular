create sequence if not exists "usuario_seq" start 1 increment 1;
CREATE TABLE IF NOT EXISTS usuario(
    id int8 NOT NULL DEFAULT nextval('usuario_seq'),
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(20) NOT NULL,
    validacao_senha VARCHAR(20) NOT NULL,
    primary key ("id")
);
