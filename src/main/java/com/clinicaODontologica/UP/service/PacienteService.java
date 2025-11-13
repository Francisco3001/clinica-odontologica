package com.clinicaODontologica.UP.service;


import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements iService<Paciente>{

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente buscar(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    public Paciente buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email).orElse(null);
    }

    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente actualizar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }
}
