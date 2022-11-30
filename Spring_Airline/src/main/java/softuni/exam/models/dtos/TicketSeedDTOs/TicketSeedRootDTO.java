package softuni.exam.models.dtos.TicketSeedDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.NONE)
public class TicketSeedRootDTO {

    @XmlElement(name = "ticket")
    private List<TicketSeedDTO> ticketDto;
}
