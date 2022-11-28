package softuni.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastDTOs.ForecastSeedRootDTO;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.Enums.DayOfWeek;
import softuni.exam.util.ValidationUtil.ValidationUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.FORECASTS_FILE_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityService cityService;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityService cityService, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.forecastRepository = forecastRepository;
        this.cityService = cityService;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        xmlParser.fromFile(FORECASTS_FILE_PATH, ForecastSeedRootDTO.class)
                .getForecasts()
                .stream()
                .filter(forecastSeedDTO -> {
                    boolean isValid = validationUtil.isValid(forecastSeedDTO);
                    if (forecastRepository.findForecastByCityWhereDaysOfWeek(cityService.FindCityById(forecastSeedDTO.getCity()),
                                    forecastSeedDTO.getDaysOfWeek()) != null) {
                        isValid = false;
                    }
                    STRING_BUILDER.append(isValid ? String.format("Successfully import forecast %s - %.2f",
                                    forecastSeedDTO.getDaysOfWeek(), forecastSeedDTO.getMaxTemperature())
                                    : "Invalid forecast")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(forecastSeedDTO -> {
                    Forecast forecast = MODEL_MAPPER.map(forecastSeedDTO, Forecast.class);
                    forecast.setCity(cityService.FindCityById(forecastSeedDTO.getCity()));
                    return forecast;
                })
                .forEach(forecastRepository::save);
        return STRING_BUILDER.toString();
    }


    @Override
    public String exportForecasts() {
        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;
        int lessPopulation = 150000;

        forecastRepository.findAllByDayOfWeekAndLessPopulation(dayOfWeek, lessPopulation)
                .forEach(forecast -> {
                    STRING_BUILDER.append(String.format("City: %s\n" +
                                            "   \t\t-min temperature: %.2f\n" +
                                            "   \t\t--max temperature: %.2f\n" +
                                            "   \t\t---sunrise: %s\n" +
                                            "   \t\t----sunset: %s\n",
                                    forecast.getCity().getCityName(), forecast.getMinTemperature(),
                                    forecast.getMaxTemperature(), forecast.getSunrise(),
                                    forecast.getSunset()))
                            .append(System.lineSeparator());
                });

        return STRING_BUILDER.toString();
    }
}
