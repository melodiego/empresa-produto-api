package br.gov.al.empresa.produto.dto.form;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmpresaFormDTO implements Serializable {
    private static final long serialVersionUID = 8456008346572035594L;

    private String cnpj;
    private String nome;
}
