package br.com.projetoAlura.ForumHub.domain.resposta;

import br.com.projetoAlura.ForumHub.domain.autor.Autor; // Certifique-se de que o pacote autor está correto
import br.com.projetoAlura.ForumHub.domain.topico.Topico; // Esta classe será criada no próximo passo
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String solucao;

    @ManyToOne(fetch = FetchType.LAZY) // Adicionado FetchType.LAZY para evitar carregamento ansioso desnecessário
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY) // Adicionado FetchType.LAZY
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    // Construtor para facilitar a criação de respostas, se necessário
    public Resposta(String solucao, Topico topico, Autor autor) {
        this.solucao = solucao;
        this.topico = topico;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now(); // Define a data de criação automaticamente
    }
}