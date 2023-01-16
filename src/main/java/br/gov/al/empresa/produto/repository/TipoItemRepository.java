package br.gov.al.empresa.produto.repository;

import br.gov.al.empresa.produto.entity.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoItemRepository extends JpaRepository<TipoItem, Long> {

}