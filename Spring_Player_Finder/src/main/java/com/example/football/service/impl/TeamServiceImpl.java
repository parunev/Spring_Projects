package com.example.football.service.impl;

import com.example.football.models.dto.TeamDTOs.TeamSeedDTO;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.Validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.example.football.util.enums.Functions.*;
import static com.example.football.util.enums.Paths.TEAM_PATH_FILE;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private ValidationUtil validationUtil;
    private TownService townService;

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAM_PATH_FILE));
    }

    @Override
    public String importTeams() throws IOException {

        Arrays.stream(GSON.fromJson(readTeamsFileContent(), TeamSeedDTO[].class))
                .filter(teamSeedDTO -> {
                    boolean isValid = validationUtil.isValid(teamSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported team %s - %s",
                                    teamSeedDTO.getName(), teamSeedDTO.getFanBase()) : "Invalid team")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(teamSeedDTO ->{
                    Team team = MODEL_MAPPER.map(teamSeedDTO, Team.class);
                    team.setTown(townService.getTownByName(teamSeedDTO.getTownName()));

                    return team;

                }).forEach(teamRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public Team getByName(String name) {
        return this.teamRepository.findByName(name);
    }
}
