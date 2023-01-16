package br.gov.al.empresa.produto.repository;

import br.gov.al.empresa.produto.entity.NotaEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotaEntradaRepository extends JpaRepository<NotaEntrada, Long> {

    List<NotaEntrada> findByEmpresaId(UUID empresaId);
}