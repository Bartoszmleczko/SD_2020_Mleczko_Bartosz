package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefuseTempDiseaseDto {

    private Long id;
    private boolean name;
    private boolean description;
    private boolean precautionDiagnose;
    private boolean interventionDiagnose;
    private boolean symptoms;
    private boolean riskFactors;
    private boolean rules;

}
