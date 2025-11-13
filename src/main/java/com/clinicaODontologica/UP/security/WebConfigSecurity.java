package com.clinicaODontologica.UP.security;

import com.clinicaODontologica.UP.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.aot.generate.ValueCodeGenerator.withDefaults;

@Configuration
@EnableWebSecurity // no van usar test de integracion--> pq se activa la seguridad
public class WebConfigSecurity {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // SOLO ADMIN puede crear, actualizar o borrar
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")

                        // USUARIO puede hacer solo GET
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ADMIN", "USUARIO")

                        .anyRequest().authenticated()
                )
                .userDetailsService(usuarioService) // 🔥 MUY IMPORTANTE
                .formLogin(form -> form.permitAll()); // 👉 Login DEFAULT

        return http.build();
    }
}
