package com.ccee.apis;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccee.dto.AgenteDTO;
import com.ccee.dto.ValorConsolidadoDTO;
import com.ccee.services.AgenteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/agentes")
@Api(value = "Agentes")
public class AgentesController {

	final AgenteService agenteService;

	public AgentesController(AgenteService agenteService) {
		this.agenteService = agenteService;
	}

	@PostMapping(consumes = "application/json")
	@ApiOperation(value = "Salvar dados xml")
	ResponseEntity<?> saveAgentes(@RequestBody List<AgenteDTO> agentesDTO) {
		try {
			agenteService.saveMany(agentesDTO);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
		}
	}

	@GetMapping("/compras-consolidadas")
	@ApiOperation(value = "Lista as compras consolidadas")
	List<ValorConsolidadoDTO> getComprasConsolidadas() {
		return agenteService.findComprasConsolidadasPorRegiao();
	}
	
	@GetMapping("/geracoes-consolidadas")
	@ApiOperation(value = "Lista as gerações consolidadas")
	List<ValorConsolidadoDTO> getGeracoesConsolidadas() {
		return agenteService.findGeracoesConsolidadasPorRegiao();
	}
}
