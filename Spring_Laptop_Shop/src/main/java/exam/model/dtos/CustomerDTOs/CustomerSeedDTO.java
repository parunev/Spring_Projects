package exam.model.dtos.CustomerDTOs;

import com.google.gson.annotations.Expose;
import exam.model.dtos.TownDTOs.TownNameDTO;
import exam.model.dtos.TownDTOs.TownNameJsonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSeedDTO {

    @Expose
    @Size(min = 2)
    @NotNull
    private String firstName;

    @Expose
    @Size(min = 2)
    @NotNull
    private String lastName;

    @Expose
    @Email
    @NotNull
    private String email;

    @Expose
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String registeredOn;

    @Expose
    private TownNameJsonDTO town;
}
