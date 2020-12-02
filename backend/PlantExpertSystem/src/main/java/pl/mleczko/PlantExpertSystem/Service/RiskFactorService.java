package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Repository.RiskFactorRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RiskFactorService {

    private final RiskFactorRepository riskFactorRepository;

    public RiskFactorService(RiskFactorRepository riskFactorRepository) {
        this.riskFactorRepository = riskFactorRepository;
    }

    @Transactional
    public RiskFactor save(RiskFactor factor){
        return riskFactorRepository.save(factor);
    }

    @Transactional
    public RiskFactor findById(Long id){

       RiskFactor factor = riskFactorRepository.findById(id).orElseThrow(( ) -> new NotFoundException(RiskFactor.class.getSimpleName()));
        return factor;
    }

    @Transactional
    public RiskFactor findBySlotName(String slotName){
        return riskFactorRepository.findBySlotName(slotName);
    }

    @Transactional
    public List<RiskFactor> findAllByNameInOrSlotNameInAndPlantType( List<String> slotNames, PlantType plantType){
        return riskFactorRepository.findAllBySlotNameInAndPlantType(slotNames,plantType);
    }

    @Transactional
    public RiskFactor findBySlotNameAndPlantType(String slotName, PlantType plantType){
        return riskFactorRepository.findBySlotNameAndPlantType(slotName, plantType);
    }


}
