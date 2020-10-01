package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.FactorType;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;
import pl.mleczko.PlantExpertSystem.Entity.Symptom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DiagnoseFormDto {

    private HashMap<FactorType, HashSet<RiskFactor>>  riskFactors;

    private Set<Symptom> symptoms;

}
