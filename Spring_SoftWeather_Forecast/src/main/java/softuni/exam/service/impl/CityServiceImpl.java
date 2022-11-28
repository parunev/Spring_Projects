package softuni.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CitySeedDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.CITIES_FILE_PATH;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, ValidationUtil validationUtil) {
        this.cityRepository = cityRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        if (areImported()){
            return "Cities are already imported!";
        }

        Arrays.stream(GSON.fromJson(readCitiesFileContent(), CitySeedDTO[].class))
                .filter(citySeedDTO -> {
                    boolean isValid = validationUtil.isValid(citySeedDTO);
                    boolean exists = checkIfCityExistsInDatabase(citySeedDTO.getCityName());

                    STRING_BUILDER.append(isValid
                                    ? String.format("Successfully imported city %s - %s"
                                    , citySeedDTO.getCityName(), citySeedDTO.getPopulation())
                                    : "Invalid city")
                            .append(System.lineSeparator());

                    return (isValid && !exists);

                }).map(citySeedDTO -> MODEL_MAPPER.map(citySeedDTO, City.class))
                .forEach(this.cityRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public City FindCityById(long city) {
        return this.cityRepository.findCityById(city);
    }

    private boolean checkIfCityExistsInDatabase(String cityName) {
        return this.cityRepository.existsByCityName(cityName);
    }
}
