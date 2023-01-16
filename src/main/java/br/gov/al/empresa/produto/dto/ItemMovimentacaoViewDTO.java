
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
public class ItemMovimentacaoViewDTO {

    private ItemViewDTO item;
    private BigDecimal valor;
    private Long quantidade;
    private BigDecimal valorDesconto;

    public String getItem() {
        return item.getNome();
    }
}
