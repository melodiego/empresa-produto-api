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
public class NotaEntradaViewDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal valorTotal;
}
