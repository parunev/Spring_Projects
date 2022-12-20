package com.example.cardealer.domain.services.CarService;

import com.example.cardealer.domain.entities.Car;
import com.example.cardealer.domain.dtos.CarDTOs.CarAndPartsDTO;

import java.util.List;

public interface CarService {

    List<Car> getCarsFromMake(String make);
    List<CarAndPartsDTO> getCarsWithTheirListOfParts();
}
