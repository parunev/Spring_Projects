package com.example.cardealer.domain.services.CarService;

import com.example.cardealer.domain.entities.Car;
import com.example.cardealer.domain.dtos.CarDTOs.CarAndPartsDTO;
import com.example.cardealer.domain.repositories.CarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private ModelMapper mapper;

    @Override
    public List<Car> getCarsFromMake(String make) {
        return this.carRepository.findByMakeOrderByModelThenByTravelledDistanceDesc(make);
    }

    @Override
    public List<CarAndPartsDTO> getCarsWithTheirListOfParts() {
        return this.carRepository.findAll().stream()
                .map(e -> mapper.map(e, CarAndPartsDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
