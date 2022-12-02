package softuni.exam.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 3, max = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    @NotNull
    private Picture picture;

    @OneToMany(targetEntity = Player.class, mappedBy = "team")
    private List<Player> players;
}
