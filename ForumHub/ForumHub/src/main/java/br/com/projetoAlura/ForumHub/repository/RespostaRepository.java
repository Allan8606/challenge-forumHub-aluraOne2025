package br.com.projetoAlura.ForumHub.repository;

import br.com.projetoAlura.ForumHub.domain.resposta.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
}