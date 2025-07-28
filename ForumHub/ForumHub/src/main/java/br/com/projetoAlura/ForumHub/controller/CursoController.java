package br.com.projetoAlura.ForumHub.controller;

import br.com.projetoAlura.ForumHub.domain.curso.Curso;
import br.com.projetoAlura.ForumHub.domain.curso.DadosCurso;
import br.com.projetoAlura.ForumHub.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarCurso(@RequestBody @Valid DadosCurso dados, UriComponentsBuilder uriBuilder){
        var curso = new Curso(dados);
        cursoRepository.save(curso);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DadosCurso>> listarCursos(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        var pagina = cursoRepository.findAll(paginacao).map(DadosCurso::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarCurso(@PathVariable Long id){
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com ID: " + id));
        return ResponseEntity.ok(new DadosCurso(curso));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarCurso(@PathVariable Long id, @RequestBody @Valid DadosCurso dados){
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado para atualização com ID: " + id));
        // Recria a entidade com os dados atualizados para simplicidade, mantendo o ID.
        curso = new Curso(dados.id(), dados.nome(), dados.categoriaCurso());
        cursoRepository.save(curso);
        return ResponseEntity.ok(new DadosCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirCurso(@PathVariable Long id){
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}