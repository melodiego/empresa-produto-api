package br.gov.al.empresa.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemEntradaDTO extends AbstractDTO {

    private String nome;
    private ItemDTO item;
    private BigDecimal valor;
    private Long quantidade;
}
