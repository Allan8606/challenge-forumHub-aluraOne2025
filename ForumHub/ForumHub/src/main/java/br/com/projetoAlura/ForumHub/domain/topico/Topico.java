package br.com.projetoAlura.ForumHub.domain.topico;

import br.com.projetoAlura.ForumHub.domain.autor.Autor;
import br.com.projetoAlura.ForumHub.domain.curso.Curso;
import br.com.projetoAlura.ForumHub.domain.resposta.Resposta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now(); // Data de criação automática
        this.status = "ABERTO"; // Status inicial padrão
        this.autor = dados.autor();
        this.curso = dados.curso();
    }

    public void atualizarDados(String titulo, String mensagem, String status, Autor autor, Curso curso) {
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (mensagem != null) {
            this.mensagem = mensagem;
        }
        if (status != null) {
            this.status = status;
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (curso != null) {
            this.curso = curso;
        }
    }
}