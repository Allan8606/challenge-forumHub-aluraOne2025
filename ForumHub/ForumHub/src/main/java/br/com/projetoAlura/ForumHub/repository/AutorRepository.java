package br.com.projetoAlura.ForumHub.repository;

import br.com.projetoAlura.ForumHub.domain.autor.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}