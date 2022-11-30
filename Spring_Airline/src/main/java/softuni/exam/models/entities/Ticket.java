package softuni.exam.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity{

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column
    private BigDecimal price;

    @Column
    private LocalDateTime takeOff;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_town_id")
    private Town fromTown;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_town_id")
    private Town toTown;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Passenger passenger;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Plane plane;
}
