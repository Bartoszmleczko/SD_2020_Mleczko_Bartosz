package pl.mleczko.PlantExpertSystem.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "temp_rules")
public class TempRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer diagnoseId;

    @ElementCollection
    @CollectionTable(name = "temp_symptoms_indexes", joinColumns = @JoinColumn(name = "id"))
    private List<Integer> symptomIndexes;

    @ElementCollection
    @CollectionTable(name = "temp_risk_factors_indexes", joinColumns = @JoinColumn(name = "id"))
    private List<Integer> riskFactorIndexes;


}
