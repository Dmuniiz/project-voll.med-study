package med.voll.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //personalização de segurança
public class SecurityConfigurations {


    @Autowired
    private SecurityFilter securityFilter;

    //para devolver um objeto ao spring utilizamos o @bean

        //configuração do processo de autenticação
    @Bean //gerenciado pelo spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //disable CSRF
        //session management -> policy -> stateless -> criar o objeto securityfilterchain
        //token protege automaticamente CSRF
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( req -> {
                    req.requestMatchers(new AntPathRequestMatcher("/login"))
                            .permitAll()
                            .anyRequest().authenticated()
                            .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
                })
                .build();
    }

    //apesar de ser uma classe do spring, para utilizar essa dependencia precisamos configura-la
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    //INFORMAR QUE AS SENHAS ESTÃO SENDO ARMAZENADAS UTILIZANDO ALGORITMO DE HASHING BCRYPT

    //código omitido

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
