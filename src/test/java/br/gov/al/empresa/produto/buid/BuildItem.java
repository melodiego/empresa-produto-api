package br.gov.al.empresa.produto.buid;

import br.gov.al.empresa.produto.dto.ItemDTO;
import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.ItemFormDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.entity.Item;
import br.gov.al.empresa.produto.entity.TipoItem;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BuildItem {

    static UUID getID() {
        return UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2");
    }

    static Item buildEntity() {
        return Item.builder()
                .id(UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2"))
                .nome("Item")
                .codigo(1L)
                .descricao("Tipo de Item")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .tipoItem(BuildTipoItem.buildEntity())
                .build();
    }

    static ItemFormDTO buildFormDTO() {
        return ItemFormDTO.builder()
                .nome("Item")
                .descricao("Tipo de Item")
                .tipoId(1L)
                .build();
    }

    static TipoItemFormDTO buildFormDTOEmpty() {
        return TipoItemFormDTO.builder().build();
    }

    static ItemDTO buildDTO() {
        return ItemDTO.builder()
                .id(UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2"))
                .nome("Item")
                .codigo(1L)
                .descricao("Tipo de Item")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .tipo(BuildTipoItem.buildDTO())
                .build();
    }

    static ItemDTO buildDTOBasic() {
        return ItemDTO.builder()
                .id(UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2"))
                .nome("Item")
                .codigo(1L)
                .descricao("Tipo de Item")
                .build();
    }
}