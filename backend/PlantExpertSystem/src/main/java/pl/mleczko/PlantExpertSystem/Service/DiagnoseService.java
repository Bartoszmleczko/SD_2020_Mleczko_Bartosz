package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.FactorType;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;
import pl.mleczko.PlantExpertSystem.Repository.RiskFactorRepository;
import pl.mleczko.PlantExpertSystem.Repository.SymptomRepository;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class DiagnoseService {

    private final RiskFactorRepository riskFactorRepository;
    private final SymptomRepository symptomRepository;

    public DiagnoseService(RiskFactorRepository riskFactorRepository, SymptomRepository symptomRepository) {
        this.riskFactorRepository = riskFactorRepository;
        this.symptomRepository = symptomRepository;
    }

    public HashMap<FactorType, HashSet<RiskFactor>> getAllFactorsByTypeAndPlantType(PlantType plantType){
        HashMap<FactorType, HashSet<RiskFactor>> result = new HashMap<>();

        for(FactorType type : FactorType.values()){
            HashSet<RiskFactor> factors = new HashSet<>(riskFactorRepository.findAllByPlantTypeAndFactorType(plantType,type));
            result.put(type,factors);
        }

        return result;
    }


}
