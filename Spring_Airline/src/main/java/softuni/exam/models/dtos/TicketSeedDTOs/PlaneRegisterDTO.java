package softuni.exam.models.dtos.TicketSeedDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.NONE)
public class PlaneRegisterDTO {

    @XmlElement(name = "register-number")
    private String registerNumber;
}
