package exam.model.dtos.ShopDTOs;

import exam.model.dtos.TownDTOs.TownNameDTO;
import exam.model.entities.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedDTO {

    @XmlElement
    @Size(min = 4)
    @NotNull
    private String address;

    @XmlElement(name = "employee-count")
    @Min(1)
    @Max(50)
    @NotNull
    private Integer employeesCount;

    @XmlElement
    @Min(2000)
    @NotNull
    private BigDecimal income;

    @XmlElement
    @Size(min = 4)
    @NotNull
    private String name;

    @XmlElement(name = "shop-area")
    @Min(150)
    @NotNull
    private Integer shopArea;

    @XmlElement(name = "town")
    private TownNameDTO townName;
}
