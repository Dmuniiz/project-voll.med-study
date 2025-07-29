package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.config.security.DadosTokenJWT;
import med.voll.api.config.security.TokenService;
import med.voll.api.domain.user.DadosAutenticacao;
import med.voll.api.domain.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AutenticacaoController {

    //  AuthenticationManager -> vai disparar minha service para carregar o usuario

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());//dto spring security
        var authenticationManager = manager.authenticate(userAuthenticationToken);//classe que despara o processo de autenticacao -> chama a service e checa se o usuário existe atraves da classe de entidade

        String tokenJWT = tokenService.gerarToken((Users) authenticationManager.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
