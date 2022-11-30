package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PassengerSeedDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.PASSENGERS_FILE;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final TownService townService;
    private final ValidationUtil validationUtil;

    public PassengerServiceImpl(PassengerRepository passengerRepository, TownService townService, ValidationUtil validationUtil) {
        this.passengerRepository = passengerRepository;
        this.townService = townService;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_FILE));
    }

    @Override
    public String importPassengers() throws IOException {

        Arrays.stream(GSON
                        .fromJson(readPassengersFileContent(), PassengerSeedDTO[].class))
                .filter(passengerSeedDTO -> {

                    boolean isValid = validationUtil.isValid(passengerSeedDTO);
                    boolean doesntExist = passengerRepository
                            .findPassengerByFirstName(passengerSeedDTO.getFirstName()).isEmpty();

                    if (!doesntExist){isValid = false;}

                    STRING_BUILDER.append(isValid ? String.format("Successfully import Passenger %s - %s",
                            passengerSeedDTO.getFirstName(), passengerSeedDTO.getEmail())
                            : "Invalid Passenger").append(System.lineSeparator());

                    return isValid;

                }).map(passengerSeedDTO -> {
                    Passenger passenger = MODEL_MAPPER.map(passengerSeedDTO, Passenger.class);
                    passenger.setTown(townService.findTownByName(passengerSeedDTO.getTown()));
                    return passenger;

                }).forEach(passengerRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {

        return this.passengerRepository
                .getByOrderByTicketsCountDescAndEmail()
                .stream()
                .map(p-> String.format( "Passenger %s  %s%n" +
                        "\tEmail - %s%n" +
                        "\tPhone - %s%n" +
                        "\tNumber of tickets - %d",
                        p.getFirstName(),
                        p.getLastName(),
                        p.getEmail(),
                        p.getPhoneNumber(),
                        p.getTickets().size()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

