package softuni.exam.instagraphlite.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PictureSeedDTO {

    @Expose
    @NotBlank
    @NotNull
    private String path;

    @Expose
    @Min(500)
    @Max(60000)
    @NotNull
    private Double size;
}
