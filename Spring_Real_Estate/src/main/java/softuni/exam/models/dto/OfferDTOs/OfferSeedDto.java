package softuni.exam.models.dto.OfferDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.dto.AgentDTOs.AgentName;
import softuni.exam.models.dto.ApartmentDTOs.ApartmentIdDto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {

    @XmlElement(name = "price")
    @Positive
    private BigDecimal price;

    @XmlElement(name = "agent")
    private AgentName name;

    @XmlElement(name = "apartment")
    private ApartmentIdDto apartment;

    @XmlElement(name = "publishedOn")
    private String publishedOn;

}
