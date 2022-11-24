package exam.service.CustomerService;

import exam.model.dtos.CustomerDTOs.CustomerSeedDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.TownService.TownService;
import exam.util.Validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.util.Enums.Functions.*;
import static exam.util.Enums.Paths.CUSTOMER_PATH_FILE;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TownService townService;
    private final ValidationUtil validationUtil;


    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMER_PATH_FILE));
    }

    @Override
    public String importCustomers() throws IOException {

        Arrays.stream(GSON.fromJson(readCustomersFileContent(), CustomerSeedDTO[].class))
                .filter(customerDto -> {
                    boolean isValid = validationUtil.isValid(customerDto);

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported Customer %s %s - %s",
                                    customerDto.getFirstName(),
                                    customerDto.getLastName(),
                                    customerDto.getEmail())
                                    : "Invalid Customer")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(customerDto ->{
                    Customer customer = MODEL_MAPPER.map(customerDto, Customer.class);
                    customer.setTown(townService.findTownByName(customerDto.getTown().getName()));

                    return customer;
                })
                .forEach(customerRepository::save);

        return STRING_BUILDER.toString();
    }
}
