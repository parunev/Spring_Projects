package softuni.exam.service.ApartmentService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentDTOs.ApartmentSeedRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.service.TownService.TownService;
import softuni.exam.util.Validation.ValidationUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.enums.Functions.STRING_BUILDER;
import static softuni.exam.util.enums.Paths.APARTMENTS_FILE_PATH;

@AllArgsConstructor
@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final TownService townService;

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {

        xmlParser
                .fromFile(APARTMENTS_FILE_PATH, ApartmentSeedRootDto.class)
                .getApartmentSeedDtoList()
                .stream()
                .filter(apartmentSeedDto -> {
                    boolean isValid = validationUtil.isValid(apartmentSeedDto);

                    boolean doesntExist = apartmentRepository.findApartmentByAreaAndTown(apartmentSeedDto.getArea(), townService.findTownByName(apartmentSeedDto.getTown()))
                            .isEmpty();

                    if (!doesntExist){isValid = false;}

                    STRING_BUILDER.append(isValid
                                    ? String.format("Successfully import apartment %s - %.2f",
                                    apartmentSeedDto.getApartmentType(), apartmentSeedDto.getArea())
                                    : "Invalid apartment")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(apartmentSeedDto -> {
                    Apartment apartment = MODEL_MAPPER.map(apartmentSeedDto, Apartment.class);
                    Town townByName = townService.findTownByName(apartmentSeedDto.getTown());
                    apartment.setTown(townByName);

                    return apartment;

                }).forEach(apartmentRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public Apartment findById(Long id) {
        return apartmentRepository.findById(id).orElse(null);
    }
}
