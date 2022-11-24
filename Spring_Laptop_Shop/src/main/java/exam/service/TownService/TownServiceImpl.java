package exam.service.TownService;

import exam.model.dtos.TownDTOs.TownNameDTO;
import exam.model.dtos.TownDTOs.TownSeedDTO;
import exam.model.dtos.TownDTOs.TownSeedRootDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.util.Validation.ValidationUtil;
import exam.util.XmlParser.XmlParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.util.Enums.Functions.MODEL_MAPPER;
import static exam.util.Enums.Paths.TOWN_PATH_FILE;

@AllArgsConstructor
@Service
public class TownServiceImpl implements TownService{

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_PATH_FILE));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        TownSeedRootDTO townSeedRootDTO = xmlParser.fromFile(TOWN_PATH_FILE, TownSeedRootDTO.class);

        return townSeedRootDTO.getTowns().stream()
                .map(this::validateAndSaveTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Town findTownByName(String town) {
        Optional<Town> townByTownName = townRepository.findByName(town);
        if (townByTownName.isEmpty()){
            return null;
        }
        return townByTownName.get();
    }

    private String validateAndSaveTown(TownSeedDTO townSeedDTO) {
        if (!validationUtil.isValid(townSeedDTO)){
            return "Invalid town";
        }

        Town town = MODEL_MAPPER.map(townSeedDTO, Town.class);
        this.townRepository.save(town);

        return String.format("Successfully imported Town %s", town.getName());
    }
}
