package br.gov.al.empresa.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemEstoqueDTO {

    private Long id;
    private ItemViewDTO item;
    private Long quantidade;
    private BigDecimal custoMedio;
    private Boolean isAtivo;
    private Boolean hasDesconto;

}
