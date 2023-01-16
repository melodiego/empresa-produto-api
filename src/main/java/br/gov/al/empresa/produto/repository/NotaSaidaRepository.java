package br.gov.al.empresa.produto.repository;

import br.gov.al.empresa.produto.entity.NotaSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotaSaidaRepository extends JpaRepository<NotaSaida, Long> {

    List<NotaSaida> findByEmpresaId(UUID empresaId);
}