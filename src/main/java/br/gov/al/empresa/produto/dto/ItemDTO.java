package br.gov.al.empresa.produto.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Builder
public class ItemDTO {

    private UUID id;
    private String nome;
    private Long codigo;
    private String descricao;
    private TipoItemDTO tipo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
