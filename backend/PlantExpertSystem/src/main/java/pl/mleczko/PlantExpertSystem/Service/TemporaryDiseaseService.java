package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryDisease;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Repository.TemporaryDiseaseRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TemporaryDiseaseService {

    private final TemporaryDiseaseRepository temporaryDiseaseRepository;

    public TemporaryDiseaseService(TemporaryDiseaseRepository temporaryDiseaseRepository) {
        this.temporaryDiseaseRepository = temporaryDiseaseRepository;
    }

    @Transactional
    public TemporaryDisease save(TemporaryDisease temporaryDisease){
        return temporaryDiseaseRepository.save(temporaryDisease);
    }

    @Transactional
    public TemporaryDisease findById(Integer id){
        return temporaryDiseaseRepository.findById(id).orElseThrow(() -> new NotFoundException(TemporaryDisease.class.getSimpleName()));
    }

    @Transactional
    public List<TemporaryDisease> findAll(){
        return temporaryDiseaseRepository.findAll();
    }


}
