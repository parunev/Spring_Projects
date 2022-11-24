package exam.service.LaptopService;

import com.google.gson.Gson;
import exam.model.dtos.LaptopDTOs.LaptopSeedDTO;
import exam.model.entities.Laptop;
import exam.repository.LaptopRepository;
import exam.service.ShopService.ShopService;
import exam.util.Enums.WarrantyType;
import exam.util.Validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static exam.util.Enums.Functions.*;
import static exam.util.Enums.Paths.LAPTOP_PATH_FILE;

@AllArgsConstructor
@Service
public class LaptopServiceImpl implements LaptopService{

    private final LaptopRepository laptopRepository;
    private final ValidationUtil validationUtil;
    private final ShopService shopService;

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_PATH_FILE));
    }

    @Override
    public String importLaptops() throws IOException {

        Arrays.stream(GSON.fromJson(readLaptopsFileContent(), LaptopSeedDTO[].class))
                .filter(laptopDto -> {
                    boolean isValid = validationUtil.isValid(laptopDto)
                            && !isValidWarranty(laptopDto.getWarrantyType());

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                    laptopDto.getMacAddress(),
                                    laptopDto.getCpuSpeed(),
                                    laptopDto.getRam(),
                                    laptopDto.getStorage())
                                    : "Invalid Laptop")
                            .append(System.lineSeparator());
                    return isValid;

                }) .map(laptopDto -> {
                    Laptop laptop = MODEL_MAPPER.map(laptopDto, Laptop.class);

                    laptop.setShop(shopService.findAllByName(laptopDto.getShop().getName()));

                    return laptop;
                })
                .forEach(laptopRepository::save);

        return STRING_BUILDER.toString();
    }

    private boolean isValidWarranty(String warrantyType) {
        WarrantyType[] values = WarrantyType.values();
        for (WarrantyType value : values){
            if (value.name().compareTo(warrantyType) > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public String exportBestLaptops() {

        laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddress()
                .forEach(laptop -> {
                    STRING_BUILDER.append(String.format("""
                                            Laptop - %s
                                            *Cpu speed - %.2f
                                            **Ram - %d
                                            ***Storage - %d
                                            ****Price - %.2f
                                            #Shop name - %s
                                            ##Town - %s
                                            """,
                                    laptop.getMacAddress(),
                                    laptop.getCpuSpeed(),
                                    laptop.getRam(),
                                    laptop.getStorage(),
                                    laptop.getPrice(),
                                    laptop.getShop().getName(),
                                    laptop.getShop().getTown().getName())
                            )
                            .append(System.lineSeparator());

                });

        return STRING_BUILDER.toString();
    }
}
