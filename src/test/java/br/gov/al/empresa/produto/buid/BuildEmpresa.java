package br.gov.al.empresa.produto.buid;

import br.gov.al.empresa.produto.dto.EmpresaDTO;
import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.EmpresaFormDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.entity.Empresa;
import br.gov.al.empresa.produto.entity.TipoItem;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BuildEmpresa {

    static Empresa buildEntity() {
        return Empresa.builder()
                .id(UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2"))
                .nome("Empresa Brasil LTDA")
                .cnpj("09630638000180")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static EmpresaFormDTO buildFormDTO() {
        return EmpresaFormDTO.builder()
                .nome("Empresa Brasil LTDA")
                .cnpj("09630638000180")
                .build();
    }

    static EmpresaDTO buildDTO() {
        return EmpresaDTO.builder()
                .id(UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2"))
                .nome("Empresa Brasil LTDA")
                .cnpj("09630638000180")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static EmpresaDTO buildDTOBasic() {
        return EmpresaDTO.builder()
                .id(UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2"))
                .nome("Empresa Brasil LTDA")
                .cnpj("09630638000180")
                .build();
    }
}