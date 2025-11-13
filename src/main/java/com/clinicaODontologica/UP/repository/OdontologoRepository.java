package com.clinicaODontologica.UP.repository;

import com.clinicaODontologica.UP.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository  extends JpaRepository<Odontologo,Long> {
}
