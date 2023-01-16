package br.gov.al.empresa.produto.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmpresaDTO implements Serializable {
    private static final long serialVersionUID = -3060199386183310156L;

    private UUID id;
    private String cnpj;
    private String nome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Collection<NotaEntradaViewDTO> entradas;
    private Collection<NotaSaidaViewDTO> saidas;
}
