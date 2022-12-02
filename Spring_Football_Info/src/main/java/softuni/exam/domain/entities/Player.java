package softuni.exam.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.Enums.Position;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 15)
    private String lastName;

    @Column(nullable = false)
    @Min(1)
    @Max(99)
    private Integer number;

    @Column(nullable = false)
    @Min(0)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false )
    @NotNull
    private Position position;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    @NotNull
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @NotNull
    private Team team;
}
