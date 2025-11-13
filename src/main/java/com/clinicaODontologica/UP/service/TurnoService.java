package com.clinicaODontologica.UP.service;

import com.clinicaODontologica.UP.dto.TurnoDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import com.clinicaODontologica.UP.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements iService<Turno>{

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public Turno guardar(Turno turno) {
        return turnoRepository.save(turno);
    }

    public Turno buscar(Long id) {
        return turnoRepository.findById(id).orElse(null);
    }

    public List<Turno> buscarTodos() {
        return turnoRepository.findAll();
    }

    public Turno actualizar(Turno turno) {
        return turnoRepository.save(turno);
    }

    public void eliminar(Long id) {
        turnoRepository.deleteById(id);
    }
}
