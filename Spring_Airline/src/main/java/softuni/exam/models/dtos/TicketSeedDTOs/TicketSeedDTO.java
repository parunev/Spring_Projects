package softuni.exam.models.dtos.TicketSeedDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDTO {

    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeOff;

    @XmlElement(name = "from-town")
    private FromTownDTO fromTown;

    @XmlElement(name = "to-town")
    private ToTownDTO toTown;

    @XmlElement(name = "passenger")
    private PassengerEmailDTO passenger;

    @XmlElement(name = "plane")
    private PlaneRegisterDTO plane;

}
