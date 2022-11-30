package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class PassengerSeedDTO {

    @Expose
    @Length(min = 2)
    private String firstName;

    @Expose
    @Length(min = 2)
    private String lastName;

    @Expose
    @Min(0)
    private Integer age;

    @Expose
    private String phoneNumber;

    @Expose
    @Email
    private String email;

    @Expose
    private String town;


}
