package exam.model.entities;

import exam.util.Enums.WarrantyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "cpu_speed")
    private Double cpuSpeed;

    @Column
    private Integer ram;

    @Column
    private Integer storage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;
}
