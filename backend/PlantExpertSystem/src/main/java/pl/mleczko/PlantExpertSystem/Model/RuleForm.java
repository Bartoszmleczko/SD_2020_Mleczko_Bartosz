package pl.mleczko.PlantExpertSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleForm {

    private List<SimpleTemplateForm> symptomParts;
    private List<SimpleTemplateForm> riskFactorParts;

    private int result;

}
