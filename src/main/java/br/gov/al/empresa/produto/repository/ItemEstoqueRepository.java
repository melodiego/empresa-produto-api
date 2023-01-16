package br.gov.al.empresa.produto.repository;

import br.gov.al.empresa.produto.entity.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {

    Optional<ItemEstoque> findByEmpresaIdAndItemId(UUID empresaId, UUID itemId);
    Optional<ItemEstoque> findByItemId(UUID itemId);
    List<ItemEstoque> findByEmpresaId(UUID empresaId);
}