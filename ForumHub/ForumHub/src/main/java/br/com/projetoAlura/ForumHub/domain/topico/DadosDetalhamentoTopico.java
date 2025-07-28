package br.com.projetoAlura.ForumHub.domain.topico;

import br.com.projetoAlura.ForumHub.domain.autor.DadosAutor; // Importar DadosAutor
import br.com.projetoAlura.ForumHub.domain.curso.DadosCurso;   // Importar DadosCurso
import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String status,
        DadosAutor autor, // Alterado de Autor para DadosAutor
        DadosCurso curso  // Alterado de Curso para DadosCurso
){
    public DadosDetalhamentoTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                new DadosAutor(topico.getAutor()), // Cria um DadosAutor a partir do Autor
                new DadosCurso(topico.getCurso())   // Cria um DadosCurso a partir do Curso
        );
    }
}