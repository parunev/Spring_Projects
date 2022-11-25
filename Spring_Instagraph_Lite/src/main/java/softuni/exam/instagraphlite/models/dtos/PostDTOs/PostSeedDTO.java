package softuni.exam.instagraphlite.models.dtos.PostDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDTO {

    @XmlElement
    @Size(min = 21)
    @NotNull
    private String caption;

    @XmlElement(name = "user")
    @NotNull
    private UserUsernameDto user;

    @XmlElement(name = "picture")
    @NotNull
    private PicturePathDto picture;
}
