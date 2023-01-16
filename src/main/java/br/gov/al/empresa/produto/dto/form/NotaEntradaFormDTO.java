package br.gov.al.empresa.produto.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotaEntradaFormDTO implements Serializable {
    private static final long serialVersionUID = 8966899097089211623L;

    private LocalDate data;
    private UUID empresaId;
    private Collection<ItemEntradaFormDTO> itensEntrada;
}
