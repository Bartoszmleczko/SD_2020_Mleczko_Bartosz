package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;

import java.util.Set;

@Getter
@Setter
public class NewDiseaseForm {

    private String diseaseName;
    private String diseaseTemplateName;
    private Set<SimpleTemplateForm> riskFactors;
    private Set<SimpleTemplateForm> symptoms;

}
