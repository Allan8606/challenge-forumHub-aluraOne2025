package br.com.projetoAlura.ForumHub.controller;

import br.com.projetoAlura.ForumHub.domain.autor.Autor;
import br.com.projetoAlura.ForumHub.domain.autor.DadosAutor;
import br.com.projetoAlura.ForumHub.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException; // Para o caso de não encontrar o autor
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
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarAutor(@RequestBody @Valid DadosAutor dados, UriComponentsBuilder uriBuilder){
        var autor = new Autor(dados);
        autorRepository.save(autor);

        var uri = uriBuilder.path("/autores/{id}").buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosAutor(autor));
    }

    @GetMapping
    public ResponseEntity<Page<DadosAutor>> listarAutores(@PageableDefault(sort = {"nome"}) Pageable paginacao){
        var pagina = autorRepository.findAll(paginacao).map(DadosAutor::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarAutor(@PathVariable Long id){
        var autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com ID: " + id));
        return ResponseEntity.ok(new DadosAutor(autor));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarAutor(@PathVariable Long id, @RequestBody @Valid DadosAutor dados){
        var autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado para atualização com ID: " + id));
        // A classe Autor atualiza os dados diretamente com os setters ou se você quiser, pode criar um método 'atualizarDados' no Autor
        // Por simplicidade, assumindo que DadosAutor pode ser usado para atualização completa
        autor = new Autor(dados.id(), dados.nome(), dados.email()); // Cria um novo autor com os dados atualizados, mantendo o ID
        autorRepository.save(autor);
        return ResponseEntity.ok(new DadosAutor(autor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirAutor(@PathVariable Long id){
        if (!autorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        autorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}