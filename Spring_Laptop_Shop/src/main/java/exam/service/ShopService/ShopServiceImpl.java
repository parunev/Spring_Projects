package exam.service.ShopService;

import exam.model.dtos.ShopDTOs.ShopSeedDTO;
import exam.model.dtos.ShopDTOs.ShopSeedRootDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.util.Validation.ValidationUtil;
import exam.util.XmlParser.XmlParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.util.Enums.Functions.MODEL_MAPPER;
import static exam.util.Enums.Paths.SHOP_PATH_FILE;

@AllArgsConstructor
@Service
public class ShopServiceImpl implements ShopService{

    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;


    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOP_PATH_FILE));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        ShopSeedRootDTO shopSeedRootDTO = xmlParser.fromFile(SHOP_PATH_FILE, ShopSeedRootDTO.class);

        return shopSeedRootDTO.getShops().stream()
                .map(this::validateAndSaveShop)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Shop findAllByName(String name) {
        return this.shopRepository.findAllByName(name);
    }

    private String validateAndSaveShop(ShopSeedDTO shopSeedDTO) {
        if (!validationUtil.isValid(shopSeedDTO)){
            return "Invalid shop";
        }
        Optional<Shop> optionalShop = this.shopRepository.findByName(shopSeedDTO.getName());
        if (optionalShop.isPresent()){
            return "Invalid shop";
        }

        Shop shop = MODEL_MAPPER.map(shopSeedDTO, Shop.class);
        Town town = this.townRepository.findByName(shopSeedDTO.getTownName().getName()).get();

        shop.setTown(town);

        this.shopRepository.save(shop);

        return String.format("Successfully imported Shop %s - %s", shop.getName(), shop.getIncome());
    }
}
