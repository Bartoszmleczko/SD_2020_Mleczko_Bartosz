package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryPlantType;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Repository.TemporaryPlantTypeRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemporaryPlantTypeService {

    private final TemporaryPlantTypeRepository temporaryPlantTypeRepository;
    private final PlantTypeService plantTypeService;

    public TemporaryPlantTypeService(TemporaryPlantTypeRepository temporaryPlantTypeRepository, PlantTypeService plantTypeService) {
        this.temporaryPlantTypeRepository = temporaryPlantTypeRepository;
        this.plantTypeService = plantTypeService;
    }

    @Transactional
    public List<TemporaryPlantType> findAll(){
        return temporaryPlantTypeRepository.findAll();
    }

    @Transactional
    public TemporaryPlantType save(TemporaryPlantType temporaryPlantType){
        temporaryPlantType.setRequestDate(LocalDateTime.now());
        return temporaryPlantTypeRepository.save(temporaryPlantType);
    }

    @Transactional
    public TemporaryPlantType findById(Long id){
        return temporaryPlantTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(TemporaryPlantType.class.getSimpleName()));
    }

    @Transactional
    public void delete(Long id){
        temporaryPlantTypeRepository.deleteById(id);
    }

    @Transactional
    public PlantType acceptRequest(TemporaryPlantType temporaryPlantType){

        PlantType plantType = new PlantType(null, temporaryPlantType.getName());
        delete(temporaryPlantType.getId());
        return plantTypeService.save(plantType);
    }

}
