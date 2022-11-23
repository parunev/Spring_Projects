package com.example.football.models.dto.TownDTOs;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownSeedDTO {

    @Expose
    @Size(min = 2)
    @NotNull
    private String name;

    @Expose
    @Positive
    @NotNull
    private Integer population;

    @Expose
    @Size(min = 10)
    @NotNull
    private String travelGuide;
}
