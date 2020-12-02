package pl.mleczko.PlantExpertSystem.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "temporary_disease_refuse_properties")
public class TemporaryDiseaseRefuseProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean name;
    private boolean description;
    private boolean precautionDiagnose;
    private boolean interventionDiagnose;
    private boolean symptoms;
    private boolean riskFactors;
    private boolean rules;

}
