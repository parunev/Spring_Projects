package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PlaneDTOs.PlaneSeedRootDTO;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil.ValidationUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.PLANES_FILE;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PlaneServiceImpl(PlaneRepository planeRepository, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_FILE));
    }

    @Override
    public String importPlanes() throws JAXBException {
        xmlParser.fromFile(PLANES_FILE, PlaneSeedRootDTO.class)
                .getPlaneSeedDTO().stream().filter(planeSeedDTO -> {
                    boolean isValid = validationUtil.isValid(planeSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully import Plane %s"
                                    , planeSeedDTO.getRegisterNumber()): "Invalid Plane")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(planeSeedDTO -> MODEL_MAPPER.map(planeSeedDTO, Plane.class))
                .forEach(planeRepository::save);

        return STRING_BUILDER.toString();
    }
}
