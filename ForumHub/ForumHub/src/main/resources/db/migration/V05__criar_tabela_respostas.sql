CREATE TABLE respostas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    solucao TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    data_criacao DATETIME NOT NULL,
    autor_id BIGINT NOT NULL,
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);