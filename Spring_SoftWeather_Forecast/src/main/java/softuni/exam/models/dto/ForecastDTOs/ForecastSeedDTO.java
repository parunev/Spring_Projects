package softuni.exam.models.dto.ForecastDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.Enums.DayOfWeek;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "forecast")
public class ForecastSeedDTO {

    @XmlElement(name = "day_of_week")
    @NotNull
    private DayOfWeek daysOfWeek;

    @XmlElement(name = "max_temperature")
    @Min(-20)
    @Max(60)
    @NotNull
    private double maxTemperature;

    @XmlElement(name = "min_temperature")
    @Min(-50)
    @Max(40)
    @NotNull
    private double minTemperature;

    @NotNull
    private String sunrise;

    @NotNull
    private String sunset;

    private long city;

}
