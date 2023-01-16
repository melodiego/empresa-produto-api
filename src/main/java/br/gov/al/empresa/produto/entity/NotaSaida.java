package br.gov.al.empresa.produto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "nota_saida")
@Data
@EqualsAndHashCode(exclude = "empresa")
public class NotaSaida extends AbstractEntity implements Serializable, Comparable<NotaSaida> {
    private static final long serialVersionUID = -1244649890475982866L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "NOTA_SAIDA_SEQ")
    @SequenceGenerator(name = "NOTA_SAIDA_SEQ", sequenceName = "NOTA_SAIDA_SEQ", allocationSize = 1)
    private Long id;
    private LocalDate data;

    @OneToMany(mappedBy = "notaSaida", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<ItemSaida> itensSaida;
    @Column(name = "valor_total", precision = 20, scale = 4)
    private BigDecimal valorTotal;
    @Column(name = "valor_desconto", precision = 20, scale = 4)
    private BigDecimal valorDesconto;
    @Column(name = "desconto")
    private Double percentualDesconto;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "EMPRESA_ID", nullable = false)
    private Empresa empresa;

    @Override
    public int compareTo(NotaSaida notaSaida) {
        return getId().compareTo(notaSaida.getId());
    }
}
