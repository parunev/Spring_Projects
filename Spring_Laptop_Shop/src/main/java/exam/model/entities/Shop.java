package exam.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Column(unique = true)
    private String name;

    @Column
    private BigDecimal income;

    @Column
    private String address;

    @Column(name = "employees_count")
    private Integer employeesCount;

    @Column(name = "shop_area")
    private Integer shopArea;

    @OneToOne
    private Town town;
}
