package softuni.exam.models.dtos.PlaneDTOs;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedRootDTO {

    @XmlElement(name = "plane")
    private Set<PlaneSeedDTO> planeSeedDTO;
}
