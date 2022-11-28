package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CitySeedDTO {

    @Expose
    @Size(min = 2, max = 60)
    @NotNull
    private String cityName;

    @Expose
    @Size(min = 2)
    private String description;

    @Expose
    @Min(500)
    @NotNull
    private Integer population;

    @Expose
    @NotNull
    private Long country;
}
