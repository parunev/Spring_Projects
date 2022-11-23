package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity   {

    @ManyToOne
    private Apartment apartment;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Agent agent;

    @Column(nullable = false)
    private LocalDate publishedOn;

}
