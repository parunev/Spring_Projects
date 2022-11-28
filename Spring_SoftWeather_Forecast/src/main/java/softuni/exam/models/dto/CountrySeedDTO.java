package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CountrySeedDTO {

    @Expose
    @Size(min = 2, max = 60)
    @NotNull
    private String countryName;

    @Expose
    @Size(min = 2, max = 20)
    @NotNull
    private String currency;
}
