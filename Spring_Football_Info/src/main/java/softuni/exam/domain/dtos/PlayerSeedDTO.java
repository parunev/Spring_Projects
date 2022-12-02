package softuni.exam.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.Enums.Position;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PlayerSeedDTO {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer number;

    @Expose
    private BigDecimal salary;

    @Expose
    private Position position;

    @Expose
    private PictureDTO picture;

    @Expose
    private TeamDTO team;
}
