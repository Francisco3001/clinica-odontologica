package com.clinicaODontologica.UP.security;

import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.entity.Usuario;
import com.clinicaODontologica.UP.entity.UsuarioRole;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import com.clinicaODontologica.UP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private BCryptPasswordEncoder codificador;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
        String pass= "admin";
        String passCifrado= codificador.encode(pass);
        Usuario usuario = new Usuario("admin","admin",passCifrado,"admin", UsuarioRole.ROLE_ADMIN);
        System.out.println("pass sin cifrar: "+pass+ " pass cifrado: "+passCifrado);
        LocalDate localDate = LocalDate.now();

        Domicilio domicilio = new Domicilio("asda", 2,"asd", "dsd");

        Paciente paciente = new Paciente("asd", "asd", 12, localDate, domicilio,"admin");

        pacienteRepository.save(paciente);
        usuarioRepository.save(usuario);
        */

    }
}
