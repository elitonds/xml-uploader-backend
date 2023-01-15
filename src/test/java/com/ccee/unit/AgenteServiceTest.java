package com.ccee.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccee.dto.AgenteDTO;
import com.ccee.dto.CompraGeracaoDTO;
import com.ccee.enums.RegiaoEnum;
import com.ccee.model.Agente;
import com.ccee.model.Compra;
import com.ccee.model.Geracao;
import com.ccee.repositories.CustomConsolidatedValuesRepository;
import com.ccee.repositories.IAgenteRepository;
import com.ccee.services.AgenteService;

@ExtendWith(MockitoExtension.class)
public class AgenteServiceTest {

	@InjectMocks
	AgenteService agenteService;

	@Mock
	IAgenteRepository agenteRepository;

	@Mock
	CustomConsolidatedValuesRepository consolidatedValuesRepository;

	@Captor
	ArgumentCaptor<Agente> agenteCaptor;

	@Test
	@DisplayName("Deve salvar os agentes e seus dados com sucesso")
	void salvarManyComSucesso() {
		List<CompraGeracaoDTO> comprasDto = new ArrayList<>();
		comprasDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.N));
		comprasDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.S));
		comprasDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.SE));
		comprasDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.NE));

		List<CompraGeracaoDTO> geracoesDto = new ArrayList<>();
		geracoesDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.N));
		geracoesDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.S));
		geracoesDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.SE));
		geracoesDto.add(new CompraGeracaoDTO(new BigDecimal(200), RegiaoEnum.NE));

		List<AgenteDTO> agentesDto = new ArrayList<>();
		AgenteDTO agenteDto = new AgenteDTO(1L, LocalDateTime.now(), comprasDto, geracoesDto);
		agentesDto.add(agenteDto);

		Agente agente = dtoToEntity(agenteDto);

		Mockito.when(agenteRepository.save(agente)).thenReturn(agente);

		agenteService.saveMany(agentesDto);

		Mockito.verify(agenteRepository).save(agenteCaptor.capture());
		Agente agenteSaved = agenteCaptor.getValue();

		assertThat(agenteSaved.getData()).isNotNull();
		assertThat(agenteSaved.getCodigo()).isNotNull();
	}

	@Test
	void itMustThrowWhenSaveMany() {
		assertThrows(Exception.class, () -> {
			agenteService.saveMany(null);
		});
	}

	private Agente dtoToEntity(AgenteDTO agenteDto) {
		List<Compra> compras = new ArrayList<>();
		List<Geracao> geracoes = new ArrayList<>();
		for (CompraGeracaoDTO compraDto : agenteDto.getCompras()) {
			Compra compra = Compra.builder().valor(compraDto.getValor()).regiao(compraDto.getRegiao()).build();
			compras.add(compra);
		}
		for (CompraGeracaoDTO geracaoDto : agenteDto.getGeracoes()) {
			Geracao geracao = Geracao.builder().valor(geracaoDto.getValor()).regiao(geracaoDto.getRegiao()).build();
			geracoes.add(geracao);
		}
		return new Agente(null, agenteDto.getCodigo(), agenteDto.getData(), compras, geracoes);
	}
}
