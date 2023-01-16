package br.gov.al.empresa.produto.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotaSaidaFormDTO implements Serializable {
    private static final long serialVersionUID = 8966899097089211623L;

    @NotNull(message = "É obrigatório informar a data")
    private LocalDate data;
    @NotNull(message = "É obrigatório informar a empresa")
    private UUID empresaId;
    private Double descontoPercentual;
    @NotNull(message = "É obrigatório informar os itens de saída")
    private Collection<ItemSaidaFormDTO> itensSaida;
}
