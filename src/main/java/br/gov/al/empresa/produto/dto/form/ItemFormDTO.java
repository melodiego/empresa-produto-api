package br.gov.al.empresa.produto.dto.form;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ItemFormDTO implements Serializable {
    private static final long serialVersionUID = 8966899097089211623L;

    private String nome;
    private String descricao;
    private Long tipoId;
}
