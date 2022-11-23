package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.enums.ApartmentType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity {

    @Column(nullable = false)
    private Double area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApartmentType apartmentType;

    @ManyToOne
    private Town town;

}
