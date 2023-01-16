package br.gov.al.empresa.produto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "item_estoque")
@Data
public class ItemEstoque extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -4681271227313778999L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ITEM_ESTOQUE_SEQ")
    @SequenceGenerator(name = "ITEM_ESTOQUE_SEQ", sequenceName = "ITEM_ESTOQUE_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "custo_medio", precision = 20, scale = 4)
    private BigDecimal custoMedio;
    @Column(name = "quantidade")
    private Long quantidade;
    @Column(name = "ativo")
    private Boolean isAtivo;
    @Column(name = "desconto")
    private Boolean hasDesconto;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "EMPRESA_ID", nullable = false)
    private Empresa empresa;
}