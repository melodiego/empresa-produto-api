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
public class NotaEntradaDTO extends AbstractDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal valorTotal;
    private Collection<ItemMovimentacaoViewDTO> itensEntrada;
}
