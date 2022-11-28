package softuni.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.COUNTRIES_FILE_PATH;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        if (areImported()){
            return "Countries are already imported!";
        }

        Arrays.stream(GSON.fromJson(readCountriesFromFile(), CountrySeedDTO[].class))
                .filter(countrySeedDTO -> {
                    boolean isValid = validationUtil.isValid(countrySeedDTO);
                    boolean exists = checkIfCountryExistsInDatabase(countrySeedDTO.getCountryName());

                    STRING_BUILDER.append(isValid
                                    ? String.format("Successfully imported country %s - %s"
                                    , countrySeedDTO.getCountryName(), countrySeedDTO.getCurrency())
                                    : "Invalid country")
                            .append(System.lineSeparator());

                    return (isValid && !exists);

                }).map(countrySeedDTO -> MODEL_MAPPER.map(countrySeedDTO, Country.class))
                .forEach(this.countryRepository::save);

        return STRING_BUILDER.toString();
    }

    private boolean checkIfCountryExistsInDatabase(String countryName) {
        return this.countryRepository.existsByCountryName(countryName);
    }
}
