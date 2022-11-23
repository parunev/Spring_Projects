package com.example.football.models.dto.PlayerDTOs;

import com.example.football.models.dto.StatIdDto;
import com.example.football.models.dto.TeamDTOs.TeamNameDto;
import com.example.football.models.dto.TownDTOs.TownNameDto;
import com.example.football.util.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.text.Position;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDTO {

    @XmlElement(name = "first-name")
    @Size(min = 2)
    @NotNull
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2)
    @NotNull
    private String lastName;

    @XmlElement
    @Email
    @NotNull
    private String email;

    @XmlElement(name = "birth-date")
    @NotNull
    private String birthDate;

    @XmlElement
    @NotNull
    private PositionType position;

    @XmlElement
    @NotNull
    private TownNameDto town;

    @XmlElement
    @NotNull
    private TeamNameDto team;

    @XmlElement
    @NotNull
    private StatIdDto stat;

}
