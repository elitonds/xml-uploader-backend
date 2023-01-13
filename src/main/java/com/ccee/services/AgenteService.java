package com.ccee.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ccee.dto.AgenteDTO;
import com.ccee.dto.CompraGeracaoDTO;
import com.ccee.dto.ValorConsolidadoDTO;
import com.ccee.model.Agente;
import com.ccee.model.Compra;
import com.ccee.model.Geracao;
import com.ccee.repositories.CustomConsolidatedValuesRepository;
import com.ccee.repositories.IAgenteRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AgenteService {

	final IAgenteRepository agenteRepository;
	final CustomConsolidatedValuesRepository consolidatedValuesRepository;

	public AgenteService(IAgenteRepository agenteRepository,
			CustomConsolidatedValuesRepository consolidatedValuesRepository) {
		this.agenteRepository = agenteRepository;
		this.consolidatedValuesRepository = consolidatedValuesRepository;
	}

	public void saveMany(List<AgenteDTO> agentesDto) {
		try {
			for (AgenteDTO agenteDto : agentesDto) {

				Agente agente = Agente.builder().codigo(agenteDto.getCodigo()).data(agenteDto.getData()).build();

				List<Compra> compras = new ArrayList<>();
				List<Geracao> geracoes = new ArrayList<>();
				for (CompraGeracaoDTO compraDto : agenteDto.getCompras()) {
					Compra compra = Compra.builder().valor(compraDto.getValor()).regiao(compraDto.getRegiao()).build();
					compras.add(compra);
				}
				for (CompraGeracaoDTO geracaoDto : agenteDto.getGeracoes()) {
					Geracao geracao = Geracao.builder().valor(geracaoDto.getValor()).regiao(geracaoDto.getRegiao())
							.build();
					geracoes.add(geracao);
				}

				agente.setCompras(compras);
				agente.setGeracoes(geracoes);

				agenteRepository.save(agente);

				System.out.println("Agente: " + agenteDto.getCodigo());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}

	}

	public List<ValorConsolidadoDTO> findComprasConsolidadasPorRegiao() {
		try {
			return this.consolidatedValuesRepository.findComprasConsolidadasPorRegiao();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public List<ValorConsolidadoDTO> findGeracoesConsolidadasPorRegiao() {
		try {
			return this.consolidatedValuesRepository.findGeracoesConsolidadasPorRegiao();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
