package br.com.projetoAlura.ForumHub.repository;

import br.com.projetoAlura.ForumHub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Pode-se adicionar métodos de busca específicos aqui mais tarde, se necessário
    boolean existsByTituloAndMensagem(String titulo, String mensagem); // Para a regra de duplicidade
}