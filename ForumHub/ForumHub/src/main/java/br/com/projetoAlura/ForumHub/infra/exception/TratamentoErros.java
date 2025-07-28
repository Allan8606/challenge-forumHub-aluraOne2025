package br.com.projetoAlura.ForumHub.infra.exception;

import br.com.projetoAlura.ForumHub.domain.ValidacaoException; // Importe a sua exceção personalizada
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoErros {

    // Trata exceções de entidade não encontrada (por exemplo, ao buscar um ID que não existe)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build(); // Retorna status 404 Not Found
    }

    // Trata exceções de argumentos de método inválidos (erros de validação do @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); // Retorna status 400 Bad Request com detalhes dos erros
    }

    // Trata exceções personalizadas de validação de regras de negócio
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegrasDeNegocio(ValidacaoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage()); // Retorna status 400 Bad Request com a mensagem da sua exceção
    }

    // Record interno para formatar os erros de validação
    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}