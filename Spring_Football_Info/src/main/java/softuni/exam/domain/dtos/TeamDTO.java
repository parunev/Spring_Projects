package softuni.exam.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {

    @Expose
    private String name;
    @Expose
    private PictureDTO pictureJsonDto;
}
