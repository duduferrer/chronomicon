package bh.app.chronomicon.config;

import bh.app.chronomicon.security.JwtAuthFilter;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthFilter jwtAuthFilter) throws Exception{
        return httpSecurity
                //CSRF = proteçao contra requisiçoes forjadas. JWT será usado no lugar
                .csrf (csrf->csrf.disable ())
                //regras de autorização
                .authorizeHttpRequests (auth-> auth
                        .requestMatchers ("/public/**", "/login").permitAll ()
                        .requestMatchers ("/admin/**").hasRole ("ADMIN")
                        .anyRequest ().authenticated ()
                )
                //Configura sessao para ser stateless
                .sessionManagement (sess -> sess.sessionCreationPolicy (SessionCreationPolicy.STATELESS))
                .addFilterBefore (jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //Constrói o httpSecurity e retorna SecurityFilterChain
                .build ();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager ();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder ();
    }

}
