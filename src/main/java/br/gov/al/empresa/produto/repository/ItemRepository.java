package br.gov.al.empresa.produto.repository;

import br.gov.al.empresa.produto.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {


    @Query(value = "SELECT max(codigo) FROM Item")
    Long getMaxCodigo();

}