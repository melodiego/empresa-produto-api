package br.gov.al.empresa.produto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipo_item")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoItem implements Serializable, Comparable<TipoItem> {
    private static final long serialVersionUID = -6179075188245457637L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TIPO_SEQ")
    @SequenceGenerator(name = "TIPO_SEQ", sequenceName = "TIPO_SEQ", allocationSize = 1)
    private Long id;
    @NotBlank
    private String nome;
    private String descricao;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @UpdateTimestamp
    protected LocalDateTime updatedAt;

    @Override
    public int compareTo(TipoItem tipoItem) {
        return getId().compareTo(tipoItem.getId());
    }
}