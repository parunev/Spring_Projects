package exam.model.dtos.LaptopDTOs;

import com.google.gson.annotations.Expose;
import exam.model.dtos.ShopDTOs.ShopNameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopSeedDTO {

    @Expose
    @Size(min = 8)
    @NotNull
    private String macAddress;

    @Expose
    @Positive
    @NotNull
    private Double cpuSpeed;

    @Expose
    @Min(8)
    @Max(128)
    @NotNull
    private Integer ram;

    @Expose
    @Min(128)
    @Max(1024)
    @NotNull
    private Integer storage;

    @Expose
    @Size(min = 10)
    @NotNull
    private String description;

    @Expose
    @Positive
    @NotNull
    private BigDecimal price;

    @Expose
    @Enumerated(EnumType.STRING)
    private String warrantyType;

    @Expose
    private ShopNameDto shop;
}
