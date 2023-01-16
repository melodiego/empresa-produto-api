package br.gov.al.empresa.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemViewDTO {

    private UUID id;
    private Long codigo;
    private String nome;
}
