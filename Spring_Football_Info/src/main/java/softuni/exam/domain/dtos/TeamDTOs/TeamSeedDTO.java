package softuni.exam.domain.dtos.TeamDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.domain.dtos.PictureDTOs.PictureSeedDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDTO {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "picture")
    private PictureSeedDTO picture;
}
