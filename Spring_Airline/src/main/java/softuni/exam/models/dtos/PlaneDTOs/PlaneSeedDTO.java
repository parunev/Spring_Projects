package softuni.exam.models.dtos.PlaneDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedDTO {

    @XmlElement(name = "register-number")
    @Size(min = 5)
    private String registerNumber;

    @XmlElement
    @Positive
    private Integer capacity;

    @XmlElement
    @Size(min = 2)
    private String airline;


}
