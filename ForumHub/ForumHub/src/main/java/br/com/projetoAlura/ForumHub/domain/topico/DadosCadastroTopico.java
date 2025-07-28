package br.com.projetoAlura.ForumHub.domain.topico;

import br.com.projetoAlura.ForumHub.domain.autor.Autor;
import br.com.projetoAlura.ForumHub.domain.curso.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

public record DadosCadastroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        String status, // Pode ser nulo para usar o padrão "ABERTO"
        @NotNull @Valid // @Valid para validar o DTO aninhado (Autor)
        Autor autor, // Por simplicidade, recebe o Autor completo
        @NotNull @Valid // @Valid para validar o DTO aninhado (Curso)
        Curso curso // Por simplicidade, recebe o Curso completo
) {
}