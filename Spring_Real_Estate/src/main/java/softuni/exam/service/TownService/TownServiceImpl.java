package softuni.exam.service.TownService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.Validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.util.enums.Functions.*;
import static softuni.exam.util.enums.Paths.TOWNS_FILE_PATH;

@AllArgsConstructor
@Service
public class TownServiceImpl implements TownService {


    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {

        Arrays.stream(GSON
                .fromJson(readTownsFileContent(), TownSeedDto[].class))
                .filter(townSeedDto -> {
                    boolean isValid = validationUtil.isValid(townSeedDto);

                    STRING_BUILDER.append(isValid
                            ? String.format("Successfully imported town %s - %s", townSeedDto.getTownName()
                            , townSeedDto.getPopulation())
                            : "Invalid town")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(townSeedDto -> MODEL_MAPPER.map(townSeedDto, Town.class))
                .forEach(townRepository::save);

        return STRING_BUILDER.toString();
    }


    @Override
    public Town findTownByName(String townName) {
        Optional<Town> townByTownName = townRepository.getTownByTownName(townName);
        if (townByTownName.isEmpty()) {
            return null;
        }
        return townByTownName.get();
    }
}
