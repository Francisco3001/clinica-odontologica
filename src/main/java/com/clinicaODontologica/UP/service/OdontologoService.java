package com.clinicaODontologica.UP.service;

import com.clinicaODontologica.UP.Exception.OdontologoExistenteException;
import com.clinicaODontologica.UP.Exception.PacienteExistenteException;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements iService<Odontologo>{

    private final OdontologoRepository odontologoRepository;

    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardar(Odontologo odontologo) {

        odontologoRepository.findByMatricula(odontologo.getMatricula()).ifPresent(p -> {
            throw new OdontologoExistenteException(
                    "Ya existe un odontologo con la matricula: " + odontologo.getMatricula()
            );
        });

        // Guardar normalmente
        return odontologoRepository.save(odontologo);
    }

    public Odontologo buscar(Long id) {
        return odontologoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        odontologoRepository.deleteById(id);
    }

    public Odontologo actualizar(Odontologo entidad) {
        return odontologoRepository.save(entidad);
    }

    public List<Odontologo> buscarTodos() {
        return odontologoRepository.findAll();
    }
}