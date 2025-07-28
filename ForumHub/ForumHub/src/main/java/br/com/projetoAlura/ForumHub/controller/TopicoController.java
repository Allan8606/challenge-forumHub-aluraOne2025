package br.com.projetoAlura.ForumHub.controller;

import br.com.projetoAlura.ForumHub.domain.topico.DadosCadastroTopico;
import br.com.projetoAlura.ForumHub.domain.topico.DadosDetalhamentoTopico;
import br.com.projetoAlura.ForumHub.domain.topico.Topico;
import br.com.projetoAlura.ForumHub.domain.ValidacaoException; // Importar a classe de exceção personalizada
import br.com.projetoAlura.ForumHub.repository.TopicoRepository;
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
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder builder){
        // Regra de negócio: A API não deve permitir o cadastro de tópicos duplicados (contendo o mesmo título e mensagem).
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com o mesmo título e mensagem.");
        }

        var topico = new Topico(dados);
        topicoRepository.save(topico);

        var uri = builder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body((new DadosDetalhamentoTopico(topico)));
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarTopico(@PathVariable Long id){
        // Regra de negócio: Solicitar o campo ID para a consulta é essencial, pois ele permite que o usuário visualize os detalhes de um tópico acessando os dados no banco.
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com ID: " + id));
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao){ // Opcional: ordenar por data de criação.
        var pagina = topicoRepository.findAll(paginacao).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(@PathVariable Long id, @RequestBody @Valid DadosCadastroTopico dados) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado para atualização com ID: " + id));

        // Regra de negócio: As mesmas regras de negócio do cadastro de um tópico devem ser realizadas também na atualização dele.
        // Implementação simples de atualização, você pode adicionar mais validações se desejar
        topico.atualizarDados(dados.titulo(), dados.mensagem(), dados.status(), dados.autor(), dados.curso());
        topicoRepository.save(topico); // O save no Spring Data JPA também faz o update

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirTopico(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}