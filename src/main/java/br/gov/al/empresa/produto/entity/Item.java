package br.gov.al.empresa.produto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "item")
@Data
@EqualsAndHashCode(exclude="tipoItem")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends AbstractEntity implements Serializable, Comparable<Item> {
    private static final long serialVersionUID = 1761500481045211053L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long codigo;
    private String nome;
    private String descricao;
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "TIPO_ITEM_ID", nullable = false)
    private TipoItem tipoItem;

    @Override
    public int compareTo(Item item) {
        return getId().compareTo(item.getId());
    }
}