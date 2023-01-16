package br.gov.al.empresa.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotaSaidaViewDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;
    private Double percentualDesconto;
}
