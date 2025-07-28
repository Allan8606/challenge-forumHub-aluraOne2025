package br.com.projetoAlura.ForumHub.controller;

import br.com.projetoAlura.ForumHub.domain.Login;
import br.com.projetoAlura.ForumHub.infra.security.DadosTokenJWT;
import br.com.projetoAlura.ForumHub.infra.security.TokenService;
import br.com.projetoAlura.ForumHub.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid Login dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = manager.authenticate(authenticationToken); // Autentica o usuário

        var tokenJWT = tokenService.gerarToken((Usuario) auth.getPrincipal()); // Gera o token JWT

        return  ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // Retorna o token
    }
}