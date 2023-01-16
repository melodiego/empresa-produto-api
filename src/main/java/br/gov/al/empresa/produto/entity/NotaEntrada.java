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
@Table(name = "nota_entrada")
@Data
@EqualsAndHashCode(exclude="empresa")
public class NotaEntrada extends AbstractEntity implements Serializable, Comparable<NotaEntrada> {
    private static final long serialVersionUID = -5877703251065724065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "NOTA_ENTRADA_SEQ")
    @SequenceGenerator(name = "NOTA_ENTRADA_SEQ", sequenceName = "NOTA_ENTRADA_SEQ", allocationSize = 1)
    private Long id;
    private LocalDate data;

    @OneToMany(mappedBy = "notaEntrada", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<ItemEntrada> itensEntrada;

    @Column(name = "valor_total", precision = 20, scale = 4)
    private BigDecimal valorTotal;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "EMPRESA_ID", nullable = false)
    private Empresa empresa;

    @Override
    public int compareTo(NotaEntrada notaEntrada) {
        return getId().compareTo(notaEntrada.getId());
    }
}
