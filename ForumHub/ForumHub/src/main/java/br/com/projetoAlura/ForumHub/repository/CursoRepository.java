package br.com.projetoAlura.ForumHub.repository;

import br.com.projetoAlura.ForumHub.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}