package br.gov.al.empresa.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotaSaidaDTO extends AbstractDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;
    private Double percentualDesconto;
    private Collection<ItemMovimentacaoViewDTO> itensSaida;
}
