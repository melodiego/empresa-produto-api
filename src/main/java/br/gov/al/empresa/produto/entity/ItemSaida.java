package br.gov.al.empresa.produto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "item_saida")
@Data
@EqualsAndHashCode(exclude="notaSaida")
public class ItemSaida implements Serializable, Comparable<ItemSaida> {
    private static final long serialVersionUID = 1068913216661252625L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ITEM_SAIDA_SEQ")
    @SequenceGenerator(name = "ITEM_SAIDA_SEQ", sequenceName = "ITEM_SAIDA_SEQ", allocationSize = 1)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;
    @Column(name = "valor", precision = 20, scale = 4)
    private BigDecimal valor;
    @Column(name = "quantidade")
    private Long quantidade;
    @Column(name = "valor_desconto", precision = 20, scale = 4)
    private BigDecimal valorDesconto;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "NOTA_SAIDA_ID", nullable = false)
    private NotaSaida notaSaida;

    @Override
    public int compareTo(ItemSaida itemSaida) {
        return getId().compareTo(itemSaida.getId());
    }
}
