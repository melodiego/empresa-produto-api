package br.gov.al.empresa.produto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "item_entrada")
@Data
@EqualsAndHashCode(exclude="notaEntrada")
public class ItemEntrada extends AbstractEntity implements Serializable, Comparable<ItemEntrada> {
    private static final long serialVersionUID = -8825481239477869741L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ITEM_ENTRADA_SEQ")
    @SequenceGenerator(name = "ITEM_ENTRADA_SEQ", sequenceName = "ITEM_ENTRADA_SEQ", allocationSize = 1)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;
    @Column(name = "valor", precision = 20, scale = 4)
    private BigDecimal valor;
    @Column(name = "quantidade")
    private Long quantidade;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "NOTA_ENTRADA_ID", nullable = false)
    private NotaEntrada notaEntrada;

    @Override
    public int compareTo(ItemEntrada itemEntrada) {
        return getId().compareTo(itemEntrada.getId());
    }
}
