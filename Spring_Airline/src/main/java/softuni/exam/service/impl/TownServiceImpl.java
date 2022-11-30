package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TownSeedDTO;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.TOWNS_FILE;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE));
    }

    @Override
    public String importTowns() throws IOException {

        Arrays.stream(GSON
                        .fromJson(readTownsFileContent(), TownSeedDTO[].class))
                .filter(townSeedDto -> {
                    boolean isValid = validationUtil.isValid(townSeedDto);

                    STRING_BUILDER.append(isValid
                                    ? String.format("Successfully imported Town %s - %s", townSeedDto.getName()
                                    , townSeedDto.getPopulation())
                                    : "Invalid Town")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(townSeedDto -> MODEL_MAPPER.map(townSeedDto, Town.class))
                .forEach(townRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public Town findTownByName(String town) {
        Optional<Town> townByTownName = townRepository.getTownByName(town);
        if (townByTownName.isEmpty()) {
            return null;
        }
        return townByTownName.get();
    }
}
