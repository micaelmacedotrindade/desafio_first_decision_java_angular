create sequence if not exists "usuario_sistema_seq" start 1 increment 1;
CREATE TABLE IF NOT EXISTS usuario_sistema(
    id int8 NOT NULL DEFAULT nextval('usuario_sistema_seq'),
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(200) NOT NULL,
    primary key ("id")
); 

--
