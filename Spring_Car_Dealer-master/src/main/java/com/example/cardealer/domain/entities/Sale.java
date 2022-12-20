package com.example.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // In one sale, only one car can be sold
    @OneToOne(optional = false)
    private Car car;

    // Each sale has one customer and a customer can buy many cars
    @ManyToOne(optional = false)
    private Customer customer;

    @Column(nullable = false)
    private int discount;

    public Sale(Car car, Customer customer, int discount) {
        this.car = car;
        this.customer = customer;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return id.equals(sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
