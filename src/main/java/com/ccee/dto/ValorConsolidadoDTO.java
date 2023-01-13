package com.ccee.dto;

import java.math.BigDecimal;

import com.ccee.enums.RegiaoEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ValorConsolidadoDTO {
	private RegiaoEnum regiao;
	private BigDecimal valor;
}
