package softuni.exam.instagraphlite.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class UserSeedDTO {

    @Expose
    @Size(min = 2, max = 18)
    @NotNull
    private String username;

    @Expose
    @Size(min = 4)
    @NotNull
    private String password;

    @Expose
    @NotBlank
    private String profilePicture;
}
