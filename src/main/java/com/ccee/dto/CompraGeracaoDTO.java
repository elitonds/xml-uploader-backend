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
public class CompraGeracaoDTO {
    private BigDecimal valor;
    private RegiaoEnum regiao;
}
