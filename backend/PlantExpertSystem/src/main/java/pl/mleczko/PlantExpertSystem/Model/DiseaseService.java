package pl.mleczko.PlantExpertSystem.Model;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.Disease;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;

import javax.transaction.Transactional;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;


    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }


    @Transactional
    public Disease save(Disease disease){
        return diseaseRepository.save(disease);
    }

    @Transactional
    public Disease findById(Long id){
        return diseaseRepository.findById(id).orElseThrow(() -> new NotFoundException(Disease.class.getSimpleName(), id));
    }

    @Transactional
    public Disease findByTemplateName(String templateName){
        return diseaseRepository.findByTemplateName(templateName);
    }

}