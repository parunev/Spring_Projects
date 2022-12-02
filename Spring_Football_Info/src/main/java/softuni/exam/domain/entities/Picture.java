package softuni.exam.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Column(nullable = false)
    @NotNull
    private String url;

    @OneToMany(targetEntity = Team.class, cascade = CascadeType.ALL, mappedBy = "picture")
    private List<Team> team;

    @OneToMany(targetEntity = Player.class, cascade = CascadeType.ALL, mappedBy = "picture")
    private List<Player> player;

}
