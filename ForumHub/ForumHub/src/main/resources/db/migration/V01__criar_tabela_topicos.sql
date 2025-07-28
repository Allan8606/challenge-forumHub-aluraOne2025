CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL UNIQUE, -- Titulo ainda permanece UNICO conforme o erro do log anterior não era nesse campo.
    mensagem TEXT NOT NULL, -- Removido UNIQUE daqui
    data_criacao DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL
);