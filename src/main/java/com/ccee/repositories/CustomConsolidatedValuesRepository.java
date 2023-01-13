package com.ccee.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ccee.dto.ValorConsolidadoDTO;

@Repository
public class CustomConsolidatedValuesRepository {

	private final EntityManager em;

	public CustomConsolidatedValuesRepository(EntityManager em) {
		this.em = em;
	}

	public List<ValorConsolidadoDTO> findComprasConsolidadasPorRegiao() {
		String query = "select NEW com.ccee.dto.ValorConsolidadoDTO(regiao, sum(valor)) from Compra group by regiao";
		return this.em.createQuery(query, ValorConsolidadoDTO.class).getResultList();
	}
	
	public List<ValorConsolidadoDTO> findGeracoesConsolidadasPorRegiao() {
		String query = "select NEW com.ccee.dto.ValorConsolidadoDTO(regiao, sum(valor)) from Geracao group by regiao";
		return this.em.createQuery(query,  ValorConsolidadoDTO.class).getResultList();
	}
}
