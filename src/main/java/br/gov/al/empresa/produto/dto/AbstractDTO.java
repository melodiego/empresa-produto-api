package br.gov.al.empresa.produto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AbstractDTO {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
