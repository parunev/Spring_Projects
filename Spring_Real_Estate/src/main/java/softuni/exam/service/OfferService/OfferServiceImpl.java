package softuni.exam.service.OfferService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferDTOs.OfferSeedRootDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.util.enums.ApartmentType;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService.AgentService;
import softuni.exam.service.ApartmentService.ApartmentService;
import softuni.exam.util.Validation.ValidationUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.util.enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.enums.Functions.STRING_BUILDER;
import static softuni.exam.util.enums.Paths.OFFERS_FILE_PATH;

@AllArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ApartmentService apartmentService;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final AgentService agentService;


    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        OfferSeedRootDto offerSeedRootDto = xmlParser.fromFile(OFFERS_FILE_PATH, OfferSeedRootDto.class);

        offerSeedRootDto.getOfferRootDtoList()
                .stream()
                .filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);

                    if (agentService.getAgentByName(offerSeedDto.getName().getName()) == null) {
                        isValid = false;
                    }

                    STRING_BUILDER.append(isValid
                            ? String.format("Successfully import offer %.2f", offerSeedDto.getPrice())
                            : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;

                }).map(offerSeedDto -> {
                    Offer offer = MODEL_MAPPER.map(offerSeedDto, Offer.class);
                    offer.setAgent(agentService.getAgentByName(offerSeedDto.getName().getName()));
                    offer.setApartment(apartmentService.findById(offerSeedDto.getApartment().getId()));

                    return offer;

                }).forEach(offerRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public String getOffersOrderByAreaThenPrice() {

        List<Offer> offerListThreeRooms = offerRepository.findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType.three_rooms);

        offerListThreeRooms
                .forEach(offer -> {
                    STRING_BUILDER.append(String.format("Agent %s %s with offer №%d:%n" +
                                    "   -Apartment area: %.2f%n" +
                                    "   --Town: %s%n" +
                                    "   ---Price: %.2f$",
                            offer.getAgent().getFirstName(),
                            offer.getAgent().getLastName(),
                            offer.getId(),
                            offer.getApartment().getArea(),
                            offer.getApartment().getTown().getTownName(),
                            offer.getPrice()))
                            .append(System.lineSeparator());
                });

        return STRING_BUILDER.toString();
    }
}
