package com.example.cardealer.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal price;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    // One supplier can supply many parts and each part can be delivered by only one supplier
    @ManyToOne(optional = false)
    private Supplier supplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return id.equals(part.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
