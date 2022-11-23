package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stats")
public class Stat extends BaseEntity{

    @Column(name = "shooting", nullable = false)
    @Positive
    private float shooting;

    @Column(name = "passing", nullable = false)
    @Positive
    private float passing;

    @Column(name = "endurance", nullable = false)
    @Positive
    private float endurance;
}
