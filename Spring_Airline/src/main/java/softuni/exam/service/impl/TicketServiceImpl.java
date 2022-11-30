package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TicketSeedDTOs.TicketSeedDTO;
import softuni.exam.models.dtos.TicketSeedDTOs.TicketSeedRootDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil.ValidationUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.TICKETS_FILE;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final PlaneRepository planeRepository;
    private final PassengerRepository passengerRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, XmlParser xmlParser,
                             ValidationUtil validationUtil, TownRepository townRepository,
                             PlaneRepository planeRepository, PassengerRepository passengerRepository) {
        this.ticketRepository = ticketRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.planeRepository = planeRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE));
    }

    @Override
    public String importTickets() throws JAXBException {

        TicketSeedRootDTO ticketsRootDto = xmlParser.fromFile(TICKETS_FILE, TicketSeedRootDTO.class);
        List<TicketSeedDTO> ticketDto = ticketsRootDto.getTicketDto();

        for (TicketSeedDTO tdto : ticketDto) {
            Optional<Ticket> bySerialNumber =  this.ticketRepository.findBySerialNumber(tdto.getSerialNumber());

            if (bySerialNumber.isEmpty() && this.validationUtil.isValid(ticketDto)){
                Ticket ticket = MODEL_MAPPER.map(ticketDto, Ticket.class);
                Town fromTown = this.townRepository.findByName(tdto.getFromTown().getName()).orElse(null);
                Town toTown = this.townRepository.findByName(tdto.getToTown().getName()).orElse(null);
                Passenger passenger = this.passengerRepository.findByEmail(tdto.getPassenger().getEmail()).orElse(null);
                Plane plane = this.planeRepository.findByRegisterNumber(tdto.getPlane().getRegisterNumber()).orElse(null);

                ticket.setFromTown(fromTown);
                ticket.setToTown(toTown);
                ticket.setPassenger(passenger);
                ticket.setPlane(plane);

                this.ticketRepository.saveAndFlush(ticket);

                STRING_BUILDER.append(String.format("Successfully imported Ticket %s - %s"
                        ,ticket.getFromTown().getName()
                        ,ticket.getToTown().getName()));
            }else {
                STRING_BUILDER.append("Invalid Ticket");
            }
            STRING_BUILDER.append(System.lineSeparator());
        }
        return STRING_BUILDER.toString().trim();
    }
}

