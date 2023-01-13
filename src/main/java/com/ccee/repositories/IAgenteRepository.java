package com.ccee.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccee.model.Agente;

public interface IAgenteRepository extends JpaRepository<Agente, UUID> {
	Agente findByCodigo(Long codigo);
}
