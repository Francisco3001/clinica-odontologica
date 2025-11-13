package com.clinicaODontologica.UP.service;

import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomicilioService implements iService<Domicilio> {

    private final DomicilioRepository domicilioRepository;

    public DomicilioService(DomicilioRepository domicilioRepository) {
        this.domicilioRepository = domicilioRepository;
    }

    @Override
    public Domicilio guardar(Domicilio entidad) {
        return domicilioRepository.save(entidad);
    }

    @Override
    public Domicilio buscar(Long id) {
        return domicilioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        domicilioRepository.deleteById(id);
    }

    @Override
    public Domicilio actualizar(Domicilio entidad) {
        return domicilioRepository.save(entidad);
    }

    @Override
    public List<Domicilio> buscarTodos() {
        return domicilioRepository.findAll();
    }
}