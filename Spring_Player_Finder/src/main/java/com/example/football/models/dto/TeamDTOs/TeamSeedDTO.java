package com.example.football.models.dto.TeamDTOs;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamSeedDTO {

    @Expose
    @Size(min = 3)
    @NotNull
    private String name;

    @Expose
    @Size(min = 3)
    @NotNull
    private String stadiumName;

    @Expose
    @Min(1000)
    @NotNull
    private Long fanBase;

    @Expose
    @Size(min = 10)
    @NotNull
    private String history;

    @Expose
    @NotNull
    private String townName;
}
