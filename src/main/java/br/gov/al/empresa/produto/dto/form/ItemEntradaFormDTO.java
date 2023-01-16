package br.gov.al.empresa.produto.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemEntradaFormDTO implements Serializable {
    private static final long serialVersionUID = 8966899097089211623L;

    private UUID itemId;
    private Long quantidade;
    private boolean isDesconto;
    private BigDecimal valor;
}
