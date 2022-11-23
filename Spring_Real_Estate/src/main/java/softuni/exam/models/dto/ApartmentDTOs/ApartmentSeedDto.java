package softuni.exam.models.dto.ApartmentDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.enums.ApartmentType;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentSeedDto {

    @XmlElement(name = "apartmentType")
    @NotNull
    private ApartmentType apartmentType;

    @XmlElement(name = "area")
    @DecimalMin(value = "40.00")
    private Double area;

    @XmlElement(name = "town")
    private String town;
}
