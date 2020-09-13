package pl.mleczko.PlantExpertSystem.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "risk_factor")
@Data
@NoArgsConstructor
public class RiskFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "risk_id")
    private Integer riskId;

    @Column(name = "name")
    private String name;

    @Column(name = "slot_name")
    private String slotName;

    @Enumerated(EnumType.STRING)
    private FactorType factorType;

    @Enumerated(EnumType.STRING)
    private PlantType plantType;

}
