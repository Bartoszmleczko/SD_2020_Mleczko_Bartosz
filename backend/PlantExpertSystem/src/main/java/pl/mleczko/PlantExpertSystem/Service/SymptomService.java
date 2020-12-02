package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.Plant;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Entity.Symptom;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Repository.SymptomRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SymptomService {

    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    @Transactional
    public Symptom save (Symptom symptom){
        return symptomRepository.save(symptom);
    }

    @Transactional
    public Symptom findById(Long id){
        return symptomRepository.findById(id).orElseThrow(() -> new NotFoundException(Symptom.class.getSimpleName()));
    }

    @Transactional
    public  Symptom findBySlotName(String slotName){
        return symptomRepository.findBySlotName(slotName);
    }

    @Transactional
    public List<Symptom> findAllByNameInOrSlotNameInAndPlantType(List<String> slotNames, PlantType plantType){
        return symptomRepository.findAllBySlotNameInAndPlantType(slotNames, plantType);
    }

    @Transactional
    public Symptom findBySlotNameAndPlantType(String slotName, PlantType plantType){
        return symptomRepository.findBySlotNameAndPlantType(slotName, plantType);
    }

}
