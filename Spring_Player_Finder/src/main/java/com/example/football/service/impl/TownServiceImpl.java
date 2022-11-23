package com.example.football.service.impl;

import com.example.football.models.dto.TownDTOs.TownSeedDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.Validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.example.football.util.enums.Functions.*;
import static com.example.football.util.enums.Paths.TOWN_PATH_FILE;

@AllArgsConstructor
@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private ValidationUtil validationUtil;


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_PATH_FILE));
    }

    @Override
    public String importTowns() throws IOException {
        Arrays.stream(GSON.fromJson(readTownsFileContent(), TownSeedDTO[].class))
                .filter(townSeedDTO -> {
                    boolean isValid = validationUtil.isValid(townSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported town %s - %s",
                            townSeedDTO.getName(), townSeedDTO.getPopulation()) : "Invalid town")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(townSeedDTO -> MODEL_MAPPER.map(townSeedDTO, Town.class))
                .forEach(townRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public Town getTownByName(String townName) {
        return this.townRepository.findFirstByName(townName);
    }
}
