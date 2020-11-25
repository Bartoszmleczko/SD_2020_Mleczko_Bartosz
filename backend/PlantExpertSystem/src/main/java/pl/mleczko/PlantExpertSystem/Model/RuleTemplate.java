package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RuleTemplate {

        private Integer diagnoseId;
        private List<Integer> symptomIndexes;
        private List<Integer> riskFactorIndexes;

}
