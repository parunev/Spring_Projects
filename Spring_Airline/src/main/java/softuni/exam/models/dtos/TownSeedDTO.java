package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class TownSeedDTO {

    @Expose
    @Size(min = 2)
    private String name;

    @Expose
    @Positive
    private Integer population;

    @Expose
    private String guide;
}
