package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends BaseEntity{

    private String name;

    @Column(name = "stadium_name")
    private String stadiumName;

    @Column(name = "fan_base")
    private Integer fanBase;

    @Column(columnDefinition = "TEXT")
    private String history;

    @OneToOne
    private Town town;
}
