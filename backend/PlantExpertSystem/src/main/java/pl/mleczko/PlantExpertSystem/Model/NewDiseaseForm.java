package pl.mleczko.PlantExpertSystem.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;

import java.util.Set;

@Getter
@Setter
public class NewDiseaseForm {

    @NotNull
    private String diseaseName;

    @NotNull
    private String diseaseTemplateName;

    @NotNull
    private Set<SimpleTemplateForm> riskFactors;

    @NotNull
    private Set<SimpleTemplateForm> symptoms;

}
