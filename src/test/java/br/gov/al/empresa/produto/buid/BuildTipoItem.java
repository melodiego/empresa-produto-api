package br.gov.al.empresa.produto.buid;

import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.entity.TipoItem;

import java.time.LocalDateTime;

public interface BuildTipoItem {

    static TipoItem buildEntity() {
        return TipoItem.builder()
                .id(1L)
                .nome("Item")
                .descricao("Tipo de Item")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static TipoItemFormDTO buildFormDTO() {
        return TipoItemFormDTO.builder()
                .nome("Item")
                .descricao("Tipo de Item")
                .build();
    }

    static TipoItemFormDTO buildFormDTOEmpty() {
        return TipoItemFormDTO.builder().build();
    }

    static TipoItemDTO buildDTO() {
        return TipoItemDTO.builder()
                .id(1L)
                .nome("Item")
                .descricao("Tipo de Item")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static TipoItemDTO buildDTOBasic() {
        return TipoItemDTO.builder()
                .id(1L)
                .nome("Item")
                .descricao("Tipo de Item")
                .build();
    }
}