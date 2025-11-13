package com.clinicaODontologica.UP.service;

import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements iService<Odontologo>{

    private final OdontologoRepository odontologoRepository;

    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardar(Odontologo entidad) {
        return odontologoRepository.save(entidad);
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