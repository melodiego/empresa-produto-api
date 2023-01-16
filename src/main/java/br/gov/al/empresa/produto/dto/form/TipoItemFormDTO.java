package br.gov.al.empresa.produto.dto.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoItemFormDTO implements Serializable {
    private static final long serialVersionUID = -3564788654177561631L;

    @NotBlank(message = "É obrigatório informar o nome")
    private String nome;
    private String descricao;
}
